package losango.controllers;

import losango.domain.Coordinate;
import losango.domain.Hexagon;
import losango.services.HexagonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marciomarinho on 8/10/15.
 *
 * Test Usage :
 *
 * curl -i -X GET -H "Content-Type:application/json" -d '{  "latitude" : "1",  "longitude" : "1" }' http://localhost:8080/hexagon
 */

@RestController
public class HexagonController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HexagonService hexagonService;


    @RequestMapping("/hexagon")
    public Hexagon hexagon(@RequestBody Coordinate coordinate) {
        log.info("Getting hexagon at:[{},{}] ", coordinate.getLatitude(), coordinate.getLongitude());
        return new Hexagon("Blue Balls");
    }

}
