package an.example.postcodes.web;

import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.web.api.PostcodeRequest;
import an.example.postcodes.service.Mapper;
import an.example.postcodes.service.PostcodesService;
import an.example.postcodes.web.api.PostcodeResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.String.format;

@RestController
@Validated
@RequestMapping(path = "/postcodes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostcodesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodesController.class);
    private final PostcodesService postcodesService;
    private final Mapper mapper;

    public PostcodesController(PostcodesService postcodesService, Mapper mapper) {
        this.postcodesService = postcodesService;
        this.mapper = mapper;
    }

    @GetMapping
    ResponseEntity<PostcodeResponse> getPostcode(@NotBlank @RequestParam("postcode") String postcode) {
        MDC.put("request.postcode", postcode);
        LOGGER.info(format("getPostcode request with queryParam postcode: %s", postcode));
        PostcodeResponse body = postcodesService.getPostcode(postcode)
                .map(mapper::toPostcodeResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<PostcodeResponse> savePostcode(@Valid @RequestBody PostcodeRequest postcode) {
        MDC.put("request.postcode", postcode.postcode());
        LOGGER.info(format("savePostcode request with postcode: %s", postcode));
        PostCodeEntity entity = postcodesService.saveOrUpdate(postcode);
        return ResponseEntity.ok(mapper.toPostcodeResponse(entity));
    }
}
