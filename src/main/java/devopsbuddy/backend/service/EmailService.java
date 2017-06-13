package devopsbuddy.backend.service;

import devopsbuddy.web.domain.frontend.Feedback;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author TheDioniz, created on 12.06.2017.
 */
public interface EmailService {

    void sendFeedbackEmail(Feedback feedback);
    void sendGenericEmailMessage(SimpleMailMessage message);


}
