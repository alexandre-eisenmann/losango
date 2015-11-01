package losango.domain;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class CylinderTest {

    public Cylinder cylinder = new Cylinder(16E-4,256.0);

    @Test
    public void testToCube() {

    }

    @Test
    public void getTestGetCentralCoordinate() {
        Hexagon hex = new Hexagon(109524,-25061);
        assertEquals(-34.72554023078691, cylinder.getCentralCoordinates(hex).getY(),0.00000001);
        assertEquals(155.1896, cylinder.getCentralCoordinates(hex).getX(), 0.00000001);
    }

    @Test
    public void testGetHexagonFromLatitudeAndLongitude() {
        Hexagon hex = cylinder.getHexagonFromPoint(0,0);
        assertEquals(0, hex.getColumn());
        assertEquals(0, hex.getRow());

        hex = cylinder.getHexagonFromPoint(-33.722024, 151.021761);
        assertEquals(-75572, hex.getColumn());
        assertEquals(108991, hex.getRow());

        hex = cylinder.getHexagonFromPoint(-33.722519, 151.023420);
        assertEquals(-75573, hex.getColumn());
        assertEquals(108992, hex.getRow());

    }

    @Test
    public void testGetNormalizedHexagon() {
        Cylinder c = new Cylinder(10,100);
        Hexagon neighbor = c.getNormalizedHexagon(0,0);
        assertEquals(0,neighbor.getColumn());
        assertEquals(0,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(1,1);
        assertEquals(1,neighbor.getColumn());
        assertEquals(1,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(10,0);
        assertEquals(0,neighbor.getColumn());
        assertEquals(0,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(11,-2);
        assertEquals(1,neighbor.getColumn());
        assertEquals(-2,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(11,-3);
        assertEquals(1,neighbor.getColumn());
        assertEquals(-3,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(9,2);
        assertEquals(-1,neighbor.getColumn());
        assertEquals(2,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(8,3);
        assertEquals(8,neighbor.getColumn());
        assertEquals(3,neighbor.getRow());

        neighbor = c.getNormalizedHexagon(-2,3);
        assertEquals(8,neighbor.getColumn());
        assertEquals(3,neighbor.getRow());

    }


}
