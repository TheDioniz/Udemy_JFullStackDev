package devopsbuddy.backend.persistance.domain.backend;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author TheDioniz, created on 14.06.2017.
 */
@Entity
public class Plan implements Serializable {

    /** Serial Version UID for Serializable classes **/
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String name;

    /** Default for JPA **/
    public Plan() {
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plan plan = (Plan) o;

        if (id != plan.id) return false;
        return name != null ? name.equals(plan.name) : plan.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
