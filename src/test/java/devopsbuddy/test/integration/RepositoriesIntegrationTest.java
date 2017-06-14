package devopsbuddy.test.integration;

import devopsbuddy.backend.persistance.domain.backend.Plan;
import devopsbuddy.backend.persistance.domain.backend.Role;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.persistance.repositories.PlanRepository;
import devopsbuddy.backend.persistance.repositories.RoleRepository;
import devopsbuddy.backend.persistance.repositories.UserRepository;
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

    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;
    private static final int BASIC_USER_ID = 1;

    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testCreateNewPlan() {
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(BASIC_PLAN_ID);
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() {
        Role userRole = createBasicRole();
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void testCreateNewUser() {
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);

        User basicUser = createBasicUser();

        UserRole userRole = new UserRole();
        userRole.setRole(basicRole);
        userRole.setUser(basicUser);

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
    private Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");
        return plan;
    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("ROLE_USER");
        return role;
    }

    private User createBasicUser() {

        User basicUser = new User();
        basicUser.setFirstName("Denis");
        basicUser.setCountry("Poland");
        basicUser.setPassword("secret");
        basicUser.setEmail("denisszczukocki@o2.pl");
        basicUser.setUsername("denis");
        basicUser.setEnabled(true);
        basicUser.setCountry("PL");
        basicUser.setPhoneNumber("123456789");
        basicUser.setDescription("A basic user");
        basicUser.setProfileImageUrl("https://blabla.images.com/basicuser");
        basicUser.setPlan((planRepository.findOne(BASIC_PLAN_ID)));

        return basicUser;
    }

}
