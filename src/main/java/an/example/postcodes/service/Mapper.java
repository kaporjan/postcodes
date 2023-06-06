package an.example.postcodes.service;

import an.example.postcodes.domain.Distance;
import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.web.api.Coordinate;
import an.example.postcodes.web.api.DistanceResponse;
import an.example.postcodes.web.api.PostcodeResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public PostcodeResponse toPostcodeResponse(PostCodeEntity postcode) {
        return new PostcodeResponse(postcode.getPostcode(), toCoordinate(postcode));
    }

    public DistanceResponse toDistanceResponse(PostCodeEntity from, PostCodeEntity to, double distance) {
        return DistanceResponse.builder()
                .fromPostCode(from.getPostcode())
                .from(toCoordinate(from))
                .toPostCode(to.getPostcode())
                .to(toCoordinate(to))
                .distance(distance)
                .unit("Km")
                .build();
    }

    public Coordinate toCoordinate(PostCodeEntity postCode) {
        return new Coordinate(postCode.getLatitude(), postCode.getLongitude());
    }

    public Distance toDistance(PostCodeEntity from, PostCodeEntity to, double distance) {
        return Distance.builder()
                .from(toCoordinate(from))
                .to(toCoordinate(to))
                .fromPostCode(from.getPostcode())
                .toPostCode(to.getPostcode())
                .distance(distance)
                .build();
    }

    public DistanceResponse toDistanceResponse(Distance distance) {
        return DistanceResponse.builder()
                .from(distance.from())
                .to(distance.to())
                .distance(distance.distance())
                .fromPostCode(distance.fromPostCode())
                .toPostCode(distance.toPostCode())
                .unit("Km")
                .build();
    }
}
