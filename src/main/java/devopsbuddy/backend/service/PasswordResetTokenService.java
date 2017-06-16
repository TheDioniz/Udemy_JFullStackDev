package devopsbuddy.backend.service;

import devopsbuddy.backend.persistance.domain.backend.PasswordResetToken;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.repositories.PasswordResetTokenRepository;
import devopsbuddy.backend.persistance.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author TheDioniz, created on 16.06.2017.
 */
@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    private static final Logger log = LoggerFactory.getLogger(PasswordResetTokenService.class);

    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {

        PasswordResetToken passwordResetToken = null;

        User user = userRepository.findByEmail(email);

        if (null != user) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, user, now, tokenExpirationInMinutes);

            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
            log.debug("Successfully created token {} for user {}", token, user.getUsername());
        } else {
            log.warn("We couldn't find a user for the given email {}", user.getEmail());
        }

        return passwordResetToken;
    }

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
