package earthquakes.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long placeId;
    private String name;
    private double latitude, longitude;

    public Long getId() {return id;}
    public Long getPlaceId() {return placeId;}

    public String getName() {return name;}
    
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}

    public void setId(long id) {this.id = id;}
    public void setPlaceId(long placeId) {this.placeId = placeId;}

    public void setName(String name) {this.name = name;}

    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    
}