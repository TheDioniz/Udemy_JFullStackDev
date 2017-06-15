package devopsbuddy.backend.persistance.repositories;

import devopsbuddy.backend.persistance.domain.backend.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author TheDioniz, created on 15.06.2017.
 */
@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    @Query("SELECT ptr FROM PasswordResetToken ptr INNER JOIN ptr.user u WHERE ptr.user.id = ?1")
    Set<PasswordResetToken> findAllByUserId(long userId);
}
