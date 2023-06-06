package an.example.postcodes.service;

import an.example.postcodes.domain.Distance;
import an.example.postcodes.entity.PostCodeEntity;
import an.example.postcodes.web.api.Coordinate;
import an.example.postcodes.web.api.DistanceResponse;
import an.example.postcodes.web.api.PostcodeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    private Mapper underTest = new Mapper();

    @Test
    void toPostcodeResponse() {
        PostCodeEntity entity = new PostCodeEntity("postCode", 1.2345, 6.7890);
        PostcodeResponse expected = new PostcodeResponse("postCode", new Coordinate(1.2345,6.7890));
        PostcodeResponse actual = underTest.toPostcodeResponse(entity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toDistanceResponse() {
        PostCodeEntity from = new PostCodeEntity("fpc", 1.2345, 6.7890);
        PostCodeEntity to = new PostCodeEntity("tpc", 1.2345, 6.7892);
        DistanceResponse expected = DistanceResponse.builder()
                .distance(1.23)
                .from(new Coordinate(1.2345,6.7890))
                .to(new Coordinate(1.2345, 6.7892))
                .fromPostCode("fpc")
                .toPostCode("tpc")
                .unit("Km")
                .build();
        DistanceResponse actual = underTest.toDistanceResponse(from, to, 1.23);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toCoordinate() {
        PostCodeEntity from = new PostCodeEntity("fpc", 1.2345, 6.7890);
        Coordinate expected = new Coordinate(1.2345, 6.7890);
        Coordinate actual = underTest.toCoordinate(from);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toDistance() {
        Distance expected = Distance.builder()
                .distance(1.23)
                .from(new Coordinate(1.2345, 6.7890))
                .to(new Coordinate(1.2345, 6.7892))
                .fromPostCode("fpc")
                .toPostCode("tpc")
                .build();
        Distance actual = underTest.toDistance(new PostCodeEntity("fpc", 1.2345, 6.7890),
                new PostCodeEntity("tpc", 1.2345, 6.7892), 1.23);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testToDistanceResponse() {
        Distance from = Distance.builder()
                .distance(1.23)
                .from(new Coordinate(1.2345, 6.7890))
                .to(new Coordinate(1.2345, 6.7892))
                .fromPostCode("fpc")
                .toPostCode("tpc")
                .build();
        DistanceResponse expected = DistanceResponse.builder()
                .distance(1.23)
                .from(new Coordinate(1.2345,6.7890))
                .to(new Coordinate(1.2345, 6.7892))
                .fromPostCode("fpc")
                .toPostCode("tpc")
                .unit("Km")
                .build();
        DistanceResponse actual = underTest.toDistanceResponse(from);
        assertThat(actual).isEqualTo(expected);
    }
}