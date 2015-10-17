package losango.services;

import losango.domain.Coordinate;
import losango.domain.Cube;
import losango.domain.Hexagon;
import losango.domain.Tile;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class HexagonService {

    public Tile getTile(double latitude, double longitude) {
        Hexagon hexagon = Hexagon.getHexagonFromCoordinates(longitude, latitude);
        Tile tile = new Tile(hexagon.toString());
        tile.setColumn(hexagon.getColumn());
        tile.setRow(hexagon.getRow());
        return tile;
    }
}
