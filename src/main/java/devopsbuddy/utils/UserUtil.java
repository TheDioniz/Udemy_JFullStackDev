package devopsbuddy.utils;

import devopsbuddy.backend.persistance.domain.backend.User;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
public class UserUtil {

    private UserUtil() {
        throw new AssertionError("Non instantiable");
    }

    public static User createBasicUser() {

        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("me@example.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456789");
        user.setCountry("PL");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/basicuser");

        return user;
    }
}
