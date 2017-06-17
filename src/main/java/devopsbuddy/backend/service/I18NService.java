package devopsbuddy.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author TheDioniz, created on 11.06.2017.
 */
@Service
public class I18NService {

    @Autowired
    MessageSource messageSource;

    public String getMessage(String messageId) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(messageId, locale);
    }

    public String getMessage(String messageId, Locale locale) {
        return messageSource.getMessage(messageId, null, locale);
    }
}
