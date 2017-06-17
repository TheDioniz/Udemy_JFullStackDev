package devopsbuddy.test.integration;

import devopsbuddy.backend.persistance.domain.backend.Role;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.service.UserService;
import devopsbuddy.enums.PlansEnum;
import devopsbuddy.enums.RolesEnum;
import devopsbuddy.utils.UserUtils;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheDioniz, created on 16.06.2017.
 */
public class AbstractServiceIntegrationTest {

    @Autowired
    protected UserService userService;

    protected User createUser(TestName testName) {
        String username = testName.getMethodName() + UUID.randomUUID();
        String email = testName.getMethodName() + "@devopsbuddy.com" + UUID.randomUUID();

        Set<UserRole> userRoles = new HashSet<>();
        User basicUser = UserUtils.createBasicUser(username, email);
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        return userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
    }
}
