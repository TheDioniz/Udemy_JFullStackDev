package devopsbuddy.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Configuration
@EnableJpaRepositories(basePackages = "devopsbuddy.backend.persistance.repositories")
@EntityScan(basePackages = "devopsbuddy.backend.persistance.domain.backend")
@EnableTransactionManagement
public class ApplicationConfig {
}
