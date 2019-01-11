import graphql.GraphQLException;
import graphql.Mutation;
import graphql.types.LoginPayload;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserTests {

    private Mutation mutation;

    @Before
    public void setup() {
        this.mutation = new Mutation(
            new UserRepo(),
            new ItemRepo(),
            new SupplierRepo(),
            new LocationRepo(),
            new CategoryRepo(),
            new RoleRepo(),
            new TransactionRepo(),
            new TransactionRuleRepo(),
            new AccountRepo(),
            new BalanceRepo(),
            new BalanceMutationRepo()
        );
    }

    @Test
    public void userShouldBeAbleToLogin() {
        String email = "unitTest@bs-htg.nl";
        String password = "habbo123";
        LoginPayload result = mutation.login(email, password);
        assertEquals("logged in user should have the same email", email, result.getUser().getEmail());
        assertNotNull("logging in should generate a token", result.getToken());
    }

    @Test(expected = GraphQLException.class)
    public void invalidLoginsShouldThrow() throws GraphQLException {
        String email = "unitTest@bs-htg.nl";
        String password = "fail login";
        LoginPayload result = mutation.login(email, password);
    }

}
