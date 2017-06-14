package devopsbuddy.backend.persistance.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
