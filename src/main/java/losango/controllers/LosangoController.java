package losango.controllers;

import losango.domain.Hexagon;
import losango.services.HexagonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LosangoController {

    @Autowired
    private HexagonService hexagonService;


    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
     public String index() {
        return "Ola tio Marcio, vamos ficar rico!";
    }

    @RequestMapping("/hexagon")
    public Hexagon hexagon(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {

        log.info("Getting hexagon at:[{},{}] ", latitude, longitude);

        return hexagonService.getHexagon(latitude, longitude);
    }

}
