package losango.services;

import losango.domain.Cube;
import losango.domain.Hexagon;
import losango.domain.Tile;
import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    public Tile getTile(double latitude, double longitude) {
        return new Tile(getHexagonFromCoordinates(longitude, latitude).toString());
    }


    public Hexagon getHexagonFromCoordinates(double x, double y) {

         double q = (x * Math.sqrt(3)/3 - y/3) / Hexagon.SIZE;
         double r = y * 2/3 / Hexagon.SIZE;

        return getRoundedHexagon(q,r);
    }


    public Hexagon getRoundedHexagon(double q, double r) {
        int rx = (int) Math.round(q);
        int ry = (int) Math.round(r);
        int rz = (int) Math.round(-q-r);

        int dx = (int) Math.abs(rx - q);
        int dy = (int) Math.abs(ry - r);
        int dz = (int) Math.abs(rz - (-q-r));

        if (dx > dy && dx > dz)
            rx = -ry-rz;
        else if (dy > dz)
            ry = -rx-rz;
        else
            rz = -rx-ry;

        return new Cube(rx,ry,rz).toHexagon();
    }

}
