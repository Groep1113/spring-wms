package graphql;

import entity.Role;
import entity.User;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class AuthContext extends GraphQLContext {

    private final User user;

    public AuthContext(User user, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * This method simply throws an exception: "Unauthorized." when the user is not authorized
     *
     * @param env the DataFetchingEnvironment
     */
    // @TODO: we could refactor this method into a role-based authorization class of some sort?
    public static void requireAuth(DataFetchingEnvironment env) {
        // for now simply check if a user is logged in
        if (((AuthContext) env.getContext()).getUser() == null)
            throw new GraphQLException("Unauthorized. Either not logged in or you are not in possession of authorized role.");
    }

    public static void requireAuth(DataFetchingEnvironment env, Set<String> authorizedRoles) {
        requireAuth(env);

        Set<Role> userRoles = ((AuthContext) env.getContext()).getUser().getRoles();
        for (Role userRole: userRoles)
            for (String authorizedRole: authorizedRoles)
                if (userRole.getName().equals(authorizedRole))
                    return;
        throw new GraphQLException("Unauthorized. Either not logged in or you are not in possession of authorized role.");
    }
}
