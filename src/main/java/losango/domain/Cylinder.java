package losango.domain;

import java.util.stream.IntStream;

public class Cylinder {

    private double hexagonWidth;
    private double hexagonSide;
    private double cylinderCircunference = 256.0;

    public Cylinder(double hexagonWidth, double cylinderCircunference) {
        this.hexagonWidth = hexagonWidth;
        this.hexagonSide = hexagonWidth/Math.sqrt(3);
        this.cylinderCircunference = cylinderCircunference;
    }

    public Hexagon getHexagonFromPoint(double x, double y) {
        double q = (x * Math.sqrt(3.0)/3.0 - y/3.0) / hexagonSide;
        double r = y * 2.0/3.0 / hexagonSide;
        Hexagon hex = getRoundedHexagon(q, r);
        return getNormalizedHexagon(hex.getColumn(), hex.getRow());
    }

    public Hexagon getNormalizedHexagon(int column, int row) {
        int nHex = (int) (cylinderCircunference/hexagonWidth);
        int start = (row / 2) * - 1;
        return new Hexagon(((nHex + ((column - start) % nHex)) % nHex) + start, row);
    }

    public Point fromLatLngToMercatorPoint(double latitude, double longitude) {
        double phi = Math.toRadians(latitude);
        double x = (longitude + 180.0) / 360.0 * cylinderCircunference;
        double y = ((1 - Math.log(Math.tan(phi) + 1 / Math.cos(phi)) / Math.PI) / 2.0) * cylinderCircunference;
        return new Point(x,y);
    }

    public Point getCentralCoordinates(Hexagon hexagon) {
        double x = hexagonSide * Math.sqrt(3) * (hexagon.getColumn() + hexagon.getRow()/2.0);
        double y = hexagonSide * 3.0/2 * hexagon.getRow();
        return new Point(x,y);
    }

    public Point[] getHexagonCorners(Hexagon hexagon) {
        Point centre = getCentralCoordinates(hexagon);
        return IntStream.range(0, 6).mapToObj(i -> {
            double angle = Math.PI / 180 * (60 * i + 30);
            return new Point(centre.getX() + hexagonSide * Math.cos(angle),
                    centre.getY() + hexagonSide * Math.sin(angle));
        }).toArray(Point[]::new);
    }

    private Hexagon getRoundedHexagon(double q, double r) {
        int rx = (int) Math.round(q);
        int ry = (int) Math.round(r);
        int rz = (int) Math.round(-q-r);

        double dx = Math.abs(rx - q);
        double dy = Math.abs(ry - r);
        double dz = Math.abs(rz - (-q-r));

        if (dx > dy && dx > dz)
            rx = -ry-rz;
        else if (dy > dz)
            ry = -rx-rz;
        else
            rz = -rx-ry;

        return new Cube(rx,ry,rz).toHexagon();
    }

}
