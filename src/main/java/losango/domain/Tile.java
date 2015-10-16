package losango.domain;

public class Tile {

    private String code;
    private Coordinate coordinate;

    public Tile(String code) {
        this.code = code;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCode() {
        return code;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

}
