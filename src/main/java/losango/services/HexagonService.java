package losango.services;

import losango.domain.Hexagon;
import org.springframework.stereotype.Component;

@Component
public class HexagonService {

    public Hexagon getHexagon(double latitude, double longitude) {

        return new Hexagon(latitude + "-" + longitude);
    }


}
