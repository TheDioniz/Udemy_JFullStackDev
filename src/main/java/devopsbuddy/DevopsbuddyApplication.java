package devopsbuddy;

import devopsbuddy.backend.persistance.domain.backend.Role;
import devopsbuddy.backend.persistance.domain.backend.User;
import devopsbuddy.backend.persistance.domain.backend.UserRole;
import devopsbuddy.backend.service.UserService;
import devopsbuddy.enums.PlansEnum;
import devopsbuddy.enums.RolesEnum;
import devopsbuddy.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DevopsbuddyApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(DevopsbuddyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DevopsbuddyApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Override
	public void run(String... strings) throws Exception {
		User user = UserUtil.createBasicUser();
		Set<UserRole> userRoles= new HashSet<>();
		userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
		log.debug("Creating user with username {}", user.getUsername());
		userService.createUser(user, PlansEnum.PRO, userRoles);
		log.info("User {} created", user.getUsername());
	}
}
