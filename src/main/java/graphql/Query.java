package graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private UserRepository userRepository;

    @Autowired
    public Query(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // These method names have to line up with the schema.graphqls field definitions
    // (these are the get methods for our graphql schema)

    public List<User> getUsers() {
        return ((List<User>) userRepository.findAll());
    }

    public User getUserByMail(String email) {
        return userRepository
            .findByEmail(email)
            .orElse(null);
    }

    public User getUser(Integer id) {
        return userRepository
            .findById(id)
            .orElse(null);
    }

}