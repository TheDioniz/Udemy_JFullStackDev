package devopsbuddy.utils;

import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.web.controllers.ForgotMyPasswordController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
public class UserUtils {

    private UserUtils() {
        throw new AssertionError("Non instantiable");
    }

    public static User createBasicUser(String username, String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456789");
        user.setCountry("PL");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/basicuser");

        return user;
    }

    public static String createPasswordResetUrl(HttpServletRequest req, long userId, String token) {
        String passwordResetUrl =
                    req.getScheme() + "://" +
                    req.getServerName() + ":" +
                    req.getServerPort() +
                    ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                    "?id=" +
                    userId +
                    "&token=" +
                    token;
        return passwordResetUrl;

    }
}