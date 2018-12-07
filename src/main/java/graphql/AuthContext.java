package graphql;

import entity.User;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            throw new GraphQLException("Unauthorized.");
    }
}
