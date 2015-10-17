package losango.domain;

import java.util.stream.IntStream;

public class Hexagon {

    public static final double SIZE = 9e-4;
    private int q;
    private int r;

    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public Cube toCube() {
        return new Cube(q,r,-q-r);
    }

    public String toString(){
        return q + "#" + r;
    }


    public int getColumn() {
        return q;
    }

    public int getRow() {
        return r;
    }


    public Coordinate getCentralCoordinates() {
        double x = SIZE * Math.sqrt(3) * (q + r/2.0);
        double y = SIZE * 3.0/2 * r;
        return new Coordinate(y,x);
    }

    public Coordinate[] getCorners() {
        Coordinate centre = getCentralCoordinates();
        return IntStream.range(0, 6).mapToObj(i -> {
            double angle = Math.PI / 180 * (60 * i + 30);
            return new Coordinate(centre.getLongitude() + SIZE * Math.cos(angle),
                    centre.getLatitude() + SIZE * Math.sin(angle));
        }).toArray(Coordinate[]::new);
    }


    public static Hexagon getHexagonFromCoordinates(double x, double y) {
        double q = (x * Math.sqrt(3)/3 - y/3) / Hexagon.SIZE;
        double r = y * 2/3 / Hexagon.SIZE;
        return getRoundedHexagon(q,r);
    }

    public static Hexagon getRoundedHexagon(double q, double r) {
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
