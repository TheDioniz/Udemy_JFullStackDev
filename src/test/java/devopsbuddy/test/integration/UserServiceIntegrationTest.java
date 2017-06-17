package devopsbuddy.test.integration;

import devopsbuddy.backend.persistance.domain.backend.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void testCreateNewUser() {

        User user = createUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

    }

}
