package an.example.postcodes.web;

import an.example.postcodes.domain.Distance;
import an.example.postcodes.service.DistanceService;
import an.example.postcodes.service.Mapper;
import an.example.postcodes.web.api.DistanceResponse;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@Validated
@RequestMapping(path = "/postcodes/distance", produces = MediaType.APPLICATION_JSON_VALUE)
public class DistanceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceController.class);
    private final DistanceService distanceService;
    private final Mapper mapper;

    public DistanceController(DistanceService distanceService, Mapper mapper) {
        this.distanceService = distanceService;
        this.mapper = mapper;
    }

    @GetMapping
    ResponseEntity<DistanceResponse> getDistance(@NotBlank @RequestParam("from") String from, @NotBlank @RequestParam("to") String to) {
        MDC.put("request.from_postcode", from);
        MDC.put("request.to_postcode", to);
        LOGGER.info(format("GetDistance request with queryParams from: %s, to: %s", from, to));
        Distance distance = distanceService.getDistance(from, to);
        DistanceResponse body = mapper.toDistanceResponse(distance);
        return ResponseEntity.ok(body);
    }
}
