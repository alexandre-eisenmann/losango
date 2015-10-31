package losango.domain;

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



}
