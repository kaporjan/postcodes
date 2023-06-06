package an.example.postcodes.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "postcodelatlng")
public class PostCodeEntity {
    public PostCodeEntity(String postcode, double latitude, double longitude) {
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PostCodeEntity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "hibernate_seq", initialValue = 1, allocationSize=1)
    private Long id;
    private String postcode;
    private double latitude;
    private double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
