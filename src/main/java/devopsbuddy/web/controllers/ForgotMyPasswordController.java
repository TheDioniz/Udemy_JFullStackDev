package devopsbuddy.web.controllers;

import devopsbuddy.backend.persistance.domain.backend.PasswordResetToken;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.service.EmailService;
import devopsbuddy.backend.service.PasswordResetTokenService;
import devopsbuddy.utils.UserUtils;
import devopsbuddy.backend.service.I18NService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TheDioniz, created on 17.06.2017.
 */
@Controller
public class ForgotMyPasswordController {

    private static final Logger log = LoggerFactory.getLogger(ForgotMyPasswordController.class);

    public static final String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailForm";
    public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";
    public static final String MAIL_SENT_KEY = "mailSent";
    public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";

    public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgotmypassword.email.text";

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.GET)
    public String forgotPasswordGet() {
        return EMAIL_ADDRESS_VIEW_NAME;
    }

    @RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.POST)
    public String forgotPasswordPost(HttpServletRequest req, @RequestParam("email") String email, Model model) {

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);
        if (null == passwordResetToken) {
            log.warn("Couldn't find a password reset token for email {}", email);
        } else {

            User user = passwordResetToken.getUser();
            String token = passwordResetToken.getToken();

            String resetPasswordUrl = UserUtils.createPasswordResetUrl(req, user.getId(), token);
            log.debug("Reset Password URL {}", resetPasswordUrl);

            // TODO: problem with locales, cannot find email_message_text_property_name in
            // messages.properties when using locales from request
//            String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, req.getLocale());
            String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("[Devopsbuddy]: How to Reset you Password");
            mailMessage.setText(emailText + "\r\n" + resetPasswordUrl);
            mailMessage.setFrom(webMasterEmail);

            emailService.sendGenericEmailMessage(mailMessage);
        }

        model.addAttribute(MAIL_SENT_KEY, "true");

        return EMAIL_ADDRESS_VIEW_NAME;
    }

}
