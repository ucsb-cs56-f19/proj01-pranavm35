package earthquakes.searches;

public class EqSearch {
    private int distance;
    private int minmag;
    private double lat, lon;
    private String location;

    public EqSearch(){

    }

    public int getDistance() { return distance; }
    public int getMinmag() { return minmag; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getLocation() { return location; }

    public void setDistance(int dist) { distance = dist; }
    public void setMinmag(int mm) { minmag = mm; }
    public void setLat(double la) { lat = la; }
    public void setLon(double lo) { lon = lo; }
    public void setLocation(String loc) { location = loc; }

}
