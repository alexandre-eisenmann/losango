package losango.services;

import losango.domain.Hexagon;
import losango.domain.Tile;
import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    public static final double TILE_SIZE = 256.0;

    public Tile getTile(double latitude, double longitude) {
        double[] coordinates = fromLatLngToMercatorPoint(latitude, longitude);
        Hexagon hexagon = Hexagon.getHexagonFromCoordinates(coordinates[0], coordinates[1]);
        Tile tile = new Tile(hexagon.toString());
        tile.setColumn(hexagon.getColumn());
        tile.setRow(hexagon.getRow());
        return tile;
    }


    public double[] fromLatLngToMercatorPoint(double latitude, double longitude) {
        double x = (longitude + 180.0) / 360.0 * TILE_SIZE;
        double y = ((1 - Math.log(Math.tan(Math.toRadians(latitude)) + 1 / Math.cos(Math.toRadians(latitude))) / Math.PI) / 2.0) * TILE_SIZE;
        return new double[] {x,y};
    }
}
