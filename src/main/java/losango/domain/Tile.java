package losango.domain;

public class Tile {

    private String code;
    private int column;
    private int row;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Tile(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }




}
