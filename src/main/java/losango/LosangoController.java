package losango;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LosangoController {

    @RequestMapping("/")
    public String index() {
        return "Ola tio Marcio, vamos ficar rico!";
    }

}
