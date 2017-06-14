package devopsbuddy.backend.persistance.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Entity
//@Table(name = "user_role")
public class UserRole implements Serializable {

    /** Serial Version UID for Serializable classes **/
    private static final long serialVersionUID = 1L;

    public UserRole() {
    }

    @Id @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Id @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
