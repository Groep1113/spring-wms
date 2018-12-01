package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import repository.UserRepository;

import java.util.Optional;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setPassword(String pw) {
        this.password = BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Autowired // This means to get the bean called userRepository
    private UserRepository userRepository;

    // @TODO: maybe return the token String instead
    public User authenticate(String email, String password) {
        Optional optUser = userRepository.findByEmail(email);
        if (!optUser.isPresent()) return null;
        User user = ((User) optUser.get());
        if (!BCrypt.checkpw(password, user.password)) return null;
        return user;
    }
}