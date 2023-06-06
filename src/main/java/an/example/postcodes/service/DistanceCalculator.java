package an.example.postcodes.service;

import an.example.postcodes.web.api.Coordinate;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculator {
    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    public double calculateDistance(Coordinate from, Coordinate to) {
        double lat1Radians = Math.toRadians(from.lat());
        double lon1Radians = Math.toRadians(from.lon());
        double lat2Radians = Math.toRadians(to.lat());
        double lon2Radians = Math.toRadians(to.lon());
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }
}
