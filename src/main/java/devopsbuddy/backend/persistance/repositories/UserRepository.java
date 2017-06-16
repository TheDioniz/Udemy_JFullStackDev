package devopsbuddy.backend.persistance.repositories;

import devopsbuddy.backend.persistance.domain.backend.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns a User given a username or null of not found
     * @param username The username
     * @return
     */
    public User findByUsername(String username);

    public User findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :userId")
    public void updateUserPassword(@Param("userId") long userId, @Param("password") String password);
}
