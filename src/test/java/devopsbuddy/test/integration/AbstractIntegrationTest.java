package devopsbuddy.test.integration;

import devopsbuddy.backend.persistance.domain.backend.Plan;
import devopsbuddy.backend.persistance.domain.backend.Role;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.persistance.repositories.PlanRepository;
import devopsbuddy.backend.persistance.repositories.RoleRepository;
import devopsbuddy.backend.persistance.repositories.UserRepository;
import devopsbuddy.enums.PlansEnum;
import devopsbuddy.enums.RolesEnum;
import devopsbuddy.utils.UserUtils;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheDioniz, created on 15.06.2017.
 */
public abstract class AbstractIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    protected User createUser(String username, String email) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);

        return basicUser;

    }

    protected Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    protected Role createRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected User createUser(TestName testName) {
        String username = testName.getMethodName() + UUID.randomUUID();
        String email = testName.getMethodName() + "@devopsbuddy.com" + UUID.randomUUID();

        User basicUser = createUser(username, email);

        return userRepository.findOne(basicUser.getId());
    }
}
