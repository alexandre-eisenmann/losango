package losango.domain;

import java.util.UUID;

public class Hexagon {

    private int column;
    private int row;

    public Hexagon(int q, int r) {
        this.column = q;
        this.row = r;
    }

    public Cube toCube() {
        return new Cube(column, row,-column - row);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
