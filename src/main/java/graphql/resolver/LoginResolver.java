package graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import entity.User;
import graphql.types.LoginPayload;

/**
 * Because the LoginPayload data type contains a complex (non-scalar)
 * object `User`, it needs this companion resolver class.
 */
public class LoginResolver implements GraphQLResolver<LoginPayload> {

    public User user(LoginPayload payload) {
        return payload.getUser();
    }
}
