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
import devopsbuddy.utils.UsersUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RepositoriesIntegrationTest {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testCreateNewPlan() {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() {
        Role userRole = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void testCreateNewUser() {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        User basicUser = UsersUtil.createBasicUser();
        basicUser.setPlan(new Plan(PlansEnum.BASIC));

        UserRole userRole = new UserRole(basicUser, basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);

        for (UserRole role : userRoles) {
            roleRepository.save(role.getRole());
        }

        basicUser = userRepository.save(basicUser);
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());

        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();

        for (UserRole ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }
    }

    // PRIVATE METHODS
    private Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    private Role createRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

}
