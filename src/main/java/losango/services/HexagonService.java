package losango.services;

import losango.domain.Hexagon;
import losango.domain.Tile;
import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    public static final int TILE_SIZE = 256;

    public Tile getTile(double latitude, double longitude) {
        double[] coordinates = fromLatLngToMercatorPoint(latitude, longitude);
        Hexagon hexagon = Hexagon.getHexagonFromCoordinates(coordinates[0], coordinates[1]);
        Tile tile = new Tile(hexagon.toString());
        tile.setColumn(hexagon.getColumn());
        tile.setRow(hexagon.getRow());
        return tile;
    }


    public double[] fromLatLngToMercatorPoint(double latitude, double longitude) {
        double x = (longitude + 180) / 360.0 * TILE_SIZE;
        double y = ((1 - Math.log(Math.tan(latitude * Math.PI / 180.0) + 1 / Math.cos(latitude * Math.PI / 180.0)) / Math.PI) / 2.0 * Math.pow(2, 0)) * TILE_SIZE;
        return new double[] {x,y};
    }
}
