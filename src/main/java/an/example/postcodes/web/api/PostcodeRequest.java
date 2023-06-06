package an.example.postcodes.web.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostcodeRequest(@NotBlank String postcode,@NotNull Coordinate coordinate) {
}
