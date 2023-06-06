package an.example.postcodes.web.api;

import lombok.Builder;


@Builder
public record PostcodeResponse(String postcode, Coordinate coordinate) {

}