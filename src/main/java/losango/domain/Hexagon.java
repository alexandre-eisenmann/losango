package losango.domain;

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

}
