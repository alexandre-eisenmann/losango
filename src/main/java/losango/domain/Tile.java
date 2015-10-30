package losango.domain;

public class Tile {

    private String code;
    private int column;
    private int row;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.code = column + "#" + row;
    }

    public String getCode() {
        return code;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
