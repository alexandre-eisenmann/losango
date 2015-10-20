package losango.services;

import junit.framework.TestCase;
import losango.domain.Coordinate;
import losango.domain.Hexagon;
import losango.domain.Tile;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class HexagonServiceTest extends TestCase {

    private HexagonService service;

    public HexagonServiceTest() {
        service = new HexagonService();
    }


    @Test
    public void testFromLatLngToMercatorPoint() {

    }
    @Test
    public void testGetTile() {

        Tile tile = service.getTile(-33.8321785,151.1981572);
        assertEquals(94199,tile.getColumn());
        assertEquals(113772,tile.getRow());

        tile = service.getTile(-33.831312,151.197991);
        assertEquals(94199, tile.getColumn());
        assertEquals(113772, tile.getRow());

        tile = service.getTile(-33.831357, 151.197948);
        assertEquals(94199, tile.getColumn());
        assertEquals(113772, tile.getRow());

        tile = service.getTile(-33.831424, 151.197870);
        assertEquals(94199, tile.getColumn());
        assertEquals(113772, tile.getRow());

        tile = service.getTile(-33.831236, 151.198042);
        assertEquals(94200, tile.getColumn());
        assertEquals(113771, tile.getRow());

    }

}