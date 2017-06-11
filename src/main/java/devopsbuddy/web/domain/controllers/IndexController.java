package devopsbuddy.web.domain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TheDioniz, created on 11.06.2017.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
