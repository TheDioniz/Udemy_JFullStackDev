package devopsbuddy.web.controllers;

import devopsbuddy.web.domain.frontend.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author TheDioniz, created on 11.06.2017.
 */
@Controller
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    
    public static final String FEEDBACK_MODEL_KEY = "feedback";

    private static final String CONTACT_US_VIEW_NAME = "contact/contact";

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contactGer(Model model) {
        Feedback feedback = new Feedback();
        model.addAttribute(feedback);
        return ContactController.CONTACT_US_VIEW_NAME;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) Feedback feedback) {
        log.debug("Feedback POJO contant: {}", feedback);
        return ContactController.CONTACT_US_VIEW_NAME;
    }
}
