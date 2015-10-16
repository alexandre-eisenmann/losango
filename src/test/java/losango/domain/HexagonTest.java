package losango.domain;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by marciomarinho on 10/10/15.
 */
public class HexagonTest {

    @Test
    public void testToCube() {

    }

    @Test
    public void testGetHexagonFromLatitudeAndLongitude() {
        Hexagon hex = Hexagon.getHexagonFromCoordinates(0, 0);
        assertEquals("0#0", hex.toString());


        hex = Hexagon.getHexagonFromCoordinates(-33.722024, 151.021761);
        assertEquals("-77567#111868", hex.toString());

        hex = Hexagon.getHexagonFromCoordinates(-33.722519, 151.023420);
        assertEquals("-77568#111869", hex.toString());

    }



}
