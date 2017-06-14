package devopsbuddy.backend.persistance.repositories;

import devopsbuddy.backend.persistance.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns a User given a username or null of not found
     * @param username The username
     * @return
     */
    public User findByUsername(String username);
}
