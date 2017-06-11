package devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TheDioniz, created on 11.06.2017.
 */
@Controller
public class CopyController {

    @RequestMapping("/about")
    public String about() {
        return "copy/about";
    }
}
