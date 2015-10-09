package losango.services;

import junit.framework.TestCase;
import losango.domain.Hexagon;
import org.junit.Test;

public class HexagonServiceTest extends TestCase {

    HexagonService service = new HexagonService();

    @Test
    public void testGetHexagonFromLatitudeAndLongitude() {
        Hexagon hex = service.getHexagonFromCoordinates(0, 0);
        assertEquals("0#0", hex.toString());

        hex = service.getHexagonFromCoordinates(-33.722024, 151.021761);
        assertEquals("-77567#111868", hex.toString());

        hex = service.getHexagonFromCoordinates(-33.722519, 151.023420);
        assertEquals("-77568#111869", hex.toString());

    }


}