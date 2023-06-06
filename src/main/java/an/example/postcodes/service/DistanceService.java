package an.example.postcodes.service;

import an.example.postcodes.domain.Distance;
import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.repository.PostCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DistanceService {
    private static Logger LOGGER = LoggerFactory.getLogger(DistanceService.class);
    private final PostCodeRepository repository;
    private final DistanceCalculator calculator;
    private final Mapper mapper;

    public DistanceService(PostCodeRepository repository, DistanceCalculator calculator, Mapper mapper) {
        this.repository = repository;
        this.calculator = calculator;
        this.mapper = mapper;
    }

    public Distance getDistance(String fromPostcode, String toPostcode) {
        LOGGER.debug("getDistance called");
        PostCodeEntity from = repository.findByPostcode(fromPostcode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PostCodeEntity to = repository.findByPostcode(toPostcode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        double distance = calculator.calculateDistance(mapper.toCoordinate(from),mapper.toCoordinate(to));
        return mapper.toDistance(from, to, distance);
    }

}
