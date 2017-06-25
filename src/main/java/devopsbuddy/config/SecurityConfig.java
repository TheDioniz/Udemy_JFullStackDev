package devopsbuddy.config;

import devopsbuddy.backend.service.UserSecurityService;
import devopsbuddy.web.controllers.ForgotMyPasswordController;
import devopsbuddy.web.controllers.SignUpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * @author TheDioniz, created on 13.06.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /** encryption SALT **/
    private static final String SALT = "ksmdksmkdmdfk;123;44mdfkmdfkm";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    Environment env;

    /** Public URLs **/
    private static final String[] PUBLIC_MATCHERS = {
        "/webjars/**",
        "/css/**",
        "/js/**",
        "/img/**",
        "/images/**",
        "/public/**",
        "/about/**",
        "/contact/**",
        "/error/**",
        "/",
        "/h2-console/**",
        ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING,
        ForgotMyPasswordController.CHANGE_PASSWORD_PATH,
        SignUpController.SIGNUP_URL_MAPPING
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] activeProfiles = env.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            if ("dev".equalsIgnoreCase(activeProfile)) {
                http.csrf().disable();
                http.headers().frameOptions().disable();
            }

            // TODO: for testing purpose in 'prod'... TO BE REMOVED
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
            .failureUrl("/login?error").permitAll()
            .and()
            .logout().permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService)
            .passwordEncoder(passwordEncoder());
    }
}
