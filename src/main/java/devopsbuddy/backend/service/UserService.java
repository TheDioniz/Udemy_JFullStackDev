package devopsbuddy.backend.service;

import devopsbuddy.backend.persistance.domain.backend.Plan;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.persistance.repositories.PlanRepository;
import devopsbuddy.backend.persistance.repositories.RoleRepository;
import devopsbuddy.backend.persistance.repositories.UserRepository;
import devopsbuddy.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.Set;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {

        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        if (user.getPassword() == null) {
            throw new NullPointerException("User password cannot be null");
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Plan plan = new Plan(plansEnum);

        // make sure plan exists in DB
        if (!planRepository.exists(plansEnum.getId())) {
            planRepository.save(plan);
        }
        
        user.setPlan(plan);

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);

        user = userRepository.save(user);

        return user;
    }

    @Transactional
    public void updateUserPassword(long userId, String password) {

        password = passwordEncoder.encode(password);
        userRepository.updateUserPassword(userId, password);
        log.debug("Password updated successfully for user {}", userId);
    }

    public User findByUserName(String username) { return userRepository.findByUsername(username); }

    public User findByEmail(String email) { return userRepository.findByEmail(email); }
}
