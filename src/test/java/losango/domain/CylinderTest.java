package losango.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class CylinderTest {

    public Cylinder cylinder = new Cylinder(9e-4,256.0);

    @Test
    public void testToCube() {

    }

    @Test
    public void getTestGetCentralCoordinate() {
        Hexagon hex = new Hexagon(109524,-25061);
        assertEquals(-33.832350000000005, cylinder.getCentralCoordinates(hex).getY(),0.00000001);
        assertEquals(151.1979030035387, cylinder.getCentralCoordinates(hex).getX(), 0.00000001);
    }

    @Test
    public void testGetHexagonFromLatitudeAndLongitude() {
        Hexagon hex = cylinder.getHexagonFromPoint(0,0);
        assertEquals(0, hex.getColumn());
        assertEquals(0, hex.getRow());

        hex = cylinder.getHexagonFromPoint(-33.722024, 151.021761);
        assertEquals(-77567, hex.getColumn());
        assertEquals(111868, hex.getRow());

        hex = cylinder.getHexagonFromPoint(-33.722519, 151.023420);
        assertEquals(-77568, hex.getColumn());
        assertEquals(111869, hex.getRow());

    }



}
