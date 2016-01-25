package losango.services;

import losango.domain.Cylinder;
import losango.domain.Hexagon;
import losango.domain.Point;
import losango.domain.Tile;

import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    private Cylinder cylinder;


    public HexagonService() {

        cylinder = new Cylinder(16e-4,256.0);
    }


    public Tile getTile(double latitude, double longitude) {
        Point point = cylinder.fromLatLngToMercatorPoint(latitude, longitude);
        Hexagon hexagon = cylinder.getHexagonFromPoint(point.getX(), point.getY());
        Tile tile = new Tile("" + hexagon.getRow(), "" + hexagon.getColumn());
        return tile;
    }


}
