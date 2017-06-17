package devopsbuddy.test.integration;

import devopsbuddy.backend.persistance.domain.backend.PasswordResetToken;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.service.PasswordResetTokenService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author TheDioniz, created on 16.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PasswordResetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Rule
    public TestName testName = new TestName();

    @Test
    public void testCreateNewTokenForUserEmail() {

        User user = createUser(testName);

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());

        Assert.assertNotNull(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getToken());
    }

    @Test
    public void testFindByToken() {
        User user = createUser(testName);
        PasswordResetToken passwordResetTokenForEmail = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        PasswordResetToken foundToken = passwordResetTokenService.findByToken(passwordResetTokenForEmail.getToken());

        Assert.assertNotNull(user);
        Assert.assertNotNull(passwordResetTokenForEmail);
        Assert.assertNotNull(foundToken);

        Assert.assertEquals(passwordResetTokenForEmail.getToken(), foundToken.getToken());
    }
}