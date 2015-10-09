package losango.domain;

public class Cube {

    private int x;
    private int y;
    private int z;

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Hexagon toHexagon() {
        return new Hexagon(x,y);
    }
}
