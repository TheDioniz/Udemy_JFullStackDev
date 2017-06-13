package devopsbuddy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author TheDioniz, created on 13.06.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
        auth.inMemoryAuthentication()
                .withUser("denis").password("denis1")
                .roles("USER");
    }
}
