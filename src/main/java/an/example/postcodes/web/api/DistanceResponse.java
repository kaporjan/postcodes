package an.example.postcodes.web.api;

import lombok.Builder;


@Builder
public record DistanceResponse(String fromPostCode, Coordinate from, String toPostCode, Coordinate to, String unit,
                               double distance) {

}