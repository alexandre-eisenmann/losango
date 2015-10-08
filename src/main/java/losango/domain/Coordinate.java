package losango.domain;

/**
 * Created by marciomarinho on 8/10/15.
 */
public class Coordinate {

    private int latitude;
    private int longitude;

    public Coordinate() {
    }

    public Coordinate(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }





}
