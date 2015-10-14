package losango.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("*")
    public String index(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        return "static/index.html";
    }
}
