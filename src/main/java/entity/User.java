package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@JsonIgnoreProperties({"password"}) // never return the password!
@Entity // This tells Hibernate to make a table out of this class
public class User {
    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private Set<Role> roles;

    // getters
    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param pw Password as a plaintext string
     */
    public void setPassword(String pw) {
        this.password = BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        Optional<Role> result = this.roles.stream()
            .parallel()
            .filter(r -> r.getId().equals(role.getId()))
            .findAny();
        result.ifPresent(r -> this.roles.remove(r));
    }
}