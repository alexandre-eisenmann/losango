package losango.domain;

import org.springframework.data.annotation.Id;

public class Tile {

    @Id
    private String id;

    private String code;
    private String column;
    private String row;

    public Tile(String row, String column) {
        this.row = row;
        this.column = column;
        this.code = column + "#" + row;
        this.id = code;
    }

    public String getCode() {
        return code;
    }

    public String getColumn() {
        return column;
    }

    public String getRow() {
        return row;
    }

    public String toString() {
        return this.code;
    }

}
