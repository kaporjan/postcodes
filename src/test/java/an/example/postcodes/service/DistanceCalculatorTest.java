package an.example.postcodes.service;

import an.example.postcodes.web.api.Coordinate;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorTest {

    private DistanceCalculator underTest = new DistanceCalculator();
    @Test
    void calculateDistanceBetweenCoordinates() {
        // given
        Coordinate budapest = new Coordinate(47.4925, 19.0514);
        Coordinate amsterdam = new Coordinate(52.3728, 4.8936);
        // when
        double distance = underTest.calculateDistance(budapest, amsterdam);
        // then
        assertThat(distance).isCloseTo(1146.7479544987627, Offset.offset(0.001));
    }

    @Test
    void calculateDistanceBetweenCoordinates2() {
        // given
        Coordinate sydney = new Coordinate(33.8587, 151.2140);
        Coordinate new_york = new Coordinate(40.730610, -73.935242);
        // when
        double distance = underTest.calculateDistance(sydney, new_york);
        // then
        assertThat(distance).isCloseTo(10519.460869940269, Offset.offset(0.001));
    }
}