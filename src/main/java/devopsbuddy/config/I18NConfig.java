package devopsbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author TheDioniz, created on 11.06.2017.
 */
@Configuration
public class I18NConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource ressourceBundle = new ReloadableResourceBundleMessageSource();
        ressourceBundle.setBasename("classpath:i18n/messages");
        ressourceBundle.setCacheSeconds(1800);
        return ressourceBundle;
    }
}
