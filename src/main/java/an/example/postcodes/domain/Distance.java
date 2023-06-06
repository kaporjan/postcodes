package an.example.postcodes.domain;

import an.example.postcodes.web.api.Coordinate;
import lombok.Builder;


@Builder
public record Distance(String fromPostCode, Coordinate from, String toPostCode, Coordinate to, String unit,
                       double distance) {

}