package graphql;

import base.TokenManager;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import entity.User;
import graphql.types.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;

@Component
public class Mutation implements GraphQLMutationResolver {

    private UserRepository userRepository;

    @Autowired
    public Mutation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginPayload login(String email, String password) throws IllegalAccessException {
        User user = userRepository
            .authenticate(email, password)
            .orElse(null);
        if (user == null) {
            throw new GraphQLException("Invalid login");
        }
        String token = TokenManager.generateToken(user);

        return new LoginPayload(token, user);
    }

}
