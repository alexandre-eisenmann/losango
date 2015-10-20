package losango.services;

import losango.domain.Cylinder;
import losango.domain.Hexagon;
import losango.domain.Point;
import losango.domain.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    private Cylinder cylinder;

    public HexagonService() {
        cylinder = new Cylinder();
    }


    public Tile getTile(double latitude, double longitude) {
        Point point = cylinder.fromLatLngToMercatorPoint(latitude, longitude);
        Hexagon hexagon = cylinder.getHexagonFromPoint(point.getX(), point.getY());
        Tile tile = new Tile(hexagon.toString());
        tile.setColumn(hexagon.getColumn());
        tile.setRow(hexagon.getRow());
        return tile;
    }


}
