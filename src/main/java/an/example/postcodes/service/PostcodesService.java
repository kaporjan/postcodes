package an.example.postcodes.service;

import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.repository.PostCodeRepository;
import an.example.postcodes.web.api.PostcodeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostcodesService {
    private static Logger LOGGER = LoggerFactory.getLogger(PostcodesService.class);
    private final PostCodeRepository repository;
    private final Mapper mapper;

    public PostcodesService(PostCodeRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<PostCodeEntity> getPostcode(String postcode) {
        return repository.findByPostcode(postcode);
    }

    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public PostCodeEntity saveOrUpdate(PostcodeRequest postcode) {
        Optional<PostCodeEntity> existing = repository.findByPostcode(postcode.postcode());
        PostCodeEntity result = null;
        if (existing.isPresent()) {
            PostCodeEntity entity = existing.get();
            entity.setLatitude(postcode.coordinate().lat());
            entity.setLongitude(postcode.coordinate().lon());
            result = repository.save(entity);
        } else {
            PostCodeEntity newEntity = new PostCodeEntity(postcode.postcode(),
                    postcode.coordinate().lat(),
                    postcode.coordinate().lon());
            result = repository.save(newEntity);
        }
        return result;
    }
}
