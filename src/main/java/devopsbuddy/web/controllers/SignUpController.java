package devopsbuddy.web.controllers;

import devopsbuddy.backend.persistance.domain.backend.Plan;
import devopsbuddy.backend.persistance.domain.backend.Role;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.service.PlanService;
import devopsbuddy.backend.service.UserService;
import devopsbuddy.enums.PlansEnum;
import devopsbuddy.enums.RolesEnum;
import devopsbuddy.utils.UserUtils;
import devopsbuddy.web.domain.frontend.BasicAccountPayload;
import devopsbuddy.web.domain.frontend.ProAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author TheDioniz, created on 25.06.2017.
 */
@Controller
public class SignUpController {

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    public static final String SIGNUP_URL_MAPPING = "/signup";
    public static final String PAYLOAD_MODEL_KEY_NAME = "payload";
    public static final String SUBSCRIPTION_VIEW_NAME = "registration/signup";
    public static final String DUPLICATED_USERNAME_KEY = "duplicatedUsername";
    public static final String DUPLICATED_EMAIL_KEY = "duplicatedEmail";
    public static final String SIGNED_UP_MESSAGE_KEY = "signedUp";
    public static final String ERROR_MESSAGE_KEY = "message";

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signupGet(@RequestParam("planId") int planId, Model model) {

        log.info("User would like to register to plan id {}", planId);

        if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()) {
            throw new IllegalArgumentException("Plan id is not valid");
        }

        model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());

        return SUBSCRIPTION_VIEW_NAME;
    }

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.POST)
    public String signedUpPost(@RequestParam(name = "planId") int planId,
                               @ModelAttribute(PAYLOAD_MODEL_KEY_NAME) @Valid ProAccountPayload payload,
                               Model model) {

        log.debug("payload: {}", payload.toString());

        if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()) {
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id does nto exists");
            return SUBSCRIPTION_VIEW_NAME;
        }

        this.checkForDuplicates(payload, model);

        boolean duplicates = false;

        List<String> errorMessages = new ArrayList<>();

        if (model.containsAttribute(DUPLICATED_USERNAME_KEY)) {
            log.warn("The uysername already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Username already exists");
            duplicates = true;
        }

        if (model.containsAttribute(DUPLICATED_EMAIL_KEY)) {
            log.warn("The email already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Email already exists");
            duplicates = true;
        }

        if (duplicates) {
            model.addAttribute(ERROR_MESSAGE_KEY, errorMessages);
        }

        log.debug("Transforming user payload into User domain object");
        User user = UserUtils.fromWebUserToDomainUser(payload);

        log.debug("Retrieving plan from the database");
        Plan selectedPlan = planService.findPlanById(planId);

        if (null == selectedPlan) {
            log.error("The plan id {} could not be found.", planId);
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id not found");
            return SUBSCRIPTION_VIEW_NAME;
        }

        user.setPlan(selectedPlan);

        User registeredUser = null;

        Set<UserRole> roles = new HashSet<>();

        if (planId == PlansEnum.BASIC.getId()) {
            roles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
            registeredUser = userService.createUser(user, PlansEnum.BASIC, roles);
        } else {
            roles.add(new UserRole(user, new Role(RolesEnum.PRO)));
            registeredUser = userService.createUser(user, PlansEnum.PRO, roles);
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                registeredUser, null, registeredUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("User created successfully");

        model.addAttribute(SIGNED_UP_MESSAGE_KEY, "true");

        return SUBSCRIPTION_VIEW_NAME;
    }

    // Private methods

    private void checkForDuplicates(BasicAccountPayload payload, Model model) {

        if (userService.findByUserName(payload.getUsername()) != null) {
            model.addAttribute(DUPLICATED_USERNAME_KEY, true);
        }

        if (userService.findByEmail(payload.getEmail()) != null) {
            model.addAttribute(DUPLICATED_EMAIL_KEY, true);
        }
    }
}


