import com.google.common.graph.Graph;
import entity.Role;
import entity.User;
import graphql.GraphQLException;
import graphql.Mutation;
import graphql.Query;
import graphql.schema.DataFetchingEnvironment;
import graphql.types.LoginPayload;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTests {

    private Mutation mutation;
    private Query query;
    private DataFetchingEnvironment mockDFE;

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
        this.query = new Query(
            new UserRepo(),
            new RoleRepo(),
            new SupplierRepo(),
            new ItemRepo(),
            new LocationRepo(),
            new CategoryRepo(),
            new TransactionRepo(),
            new TransactionRuleRepo(),
            new AccountRepo(),
            new BalanceRepo(),
            new BalanceMutationRepo()
        );
        this.mockDFE = new MockDFE();
    }

    // Mutation tests
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

    @Test
    public void userShouldBeCreatable() {
        User user = mutation.createUser("unit", "test", "unitTest@bs-htg.nl", "habbo123", this.mockDFE);
        assertNotNull("created user object should not be null", user);
    }

    @Test
    public void rolesCanBeAdded() {
        User userWithAdminRole = mutation.addRole(1, 1, this.mockDFE);
        assertEquals("The user should have 1 role now", 1, userWithAdminRole.getRoles().size());
        Role firstUserRole = ((Role) userWithAdminRole.getRoles().toArray()[0]);
        assertEquals("The user should have a role of admin", "admin", firstUserRole.getName());

        User userWithSalesRole = mutation.addRole(1, 2, this.mockDFE);
        Role secondUserRole = ((Role) userWithSalesRole.getRoles().toArray()[1]);
        assertEquals("The user should have a role of sales", "inkoop", secondUserRole.getName());
    }

    @Test(expected = GraphQLException.class)
    public void addingInvalidRoleThrows() throws GraphQLException {
        mutation.addRole(1, -1, this.mockDFE);
    }

    @Test(expected = GraphQLException.class)
    public void addingRoleWithInvalidUserThrows() throws GraphQLException {
        mutation.addRole(-1, 1, this.mockDFE);
    }

    @Test
    public void rolesCanBeRemoved() {
        User userWithAdminRole = mutation.addRole(1, 1, this.mockDFE);
        assertEquals("The user should have 1 role now", 1, userWithAdminRole.getRoles().size());

        User userWithoutAdmin = mutation.removeRole(1, 1, this.mockDFE);
        assertEquals("The user should have 0 roles now", 0, userWithoutAdmin.getRoles().size());
    }

    @Test(expected = GraphQLException.class)
    public void removingInvalidRoleThrows() throws GraphQLException {
        mutation.removeRole(1, -1, this.mockDFE);
    }

    @Test(expected = GraphQLException.class)
    public void removingRoleWithInvalidUserThrows() throws GraphQLException {
        mutation.removeRole(-1, 1, this.mockDFE);
    }

    @Test
    public void userShouldBeUpdatable() {
        User initialUser = query.getUser(1, this.mockDFE);
        assertEquals("User has a name of unit", "unit", initialUser.getFirstName());
        User updatedUser = mutation.updateUser(1, "updated", "thename", "newmail@bs-htg.nl", "newPassword", this.mockDFE);
        assertEquals("User should now be updated", "updated", updatedUser.getFirstName());
    }

    @Test(expected = GraphQLException.class)
    public void updatingWithInvalidIdThrows() throws GraphQLException {
        mutation.updateUser(-1, "", "", "", "", this.mockDFE);
    }

    // Query tests
    @Test
    public void getUsersShouldReturnList() {
        List<User> result = query.getUsers(this.mockDFE);
        User firstUser = result.get(0);
        assertEquals("The first user should have a name of unit", "unit", firstUser.getFirstName());
    }

    @Test
    public void getUserShouldReturn() {
        User user = query.getUser(1, this.mockDFE);
        assertEquals("user should have a name of unit", "unit", user.getFirstName());
        assertEquals("user should have a lastName of test", "test", user.getLastName());
    }

    @Test
    public void getUserByEmailShouldReturn() {
        User user = query.getUserByMail("unitTest@bs-htg.nl", this.mockDFE);
        assertEquals("user should have a name of unit", "unit", user.getFirstName());
        assertEquals("user should have a lastName of test", "test", user.getLastName());
    }

    @Test
    public void currentUserShouldReturnFromMockContext() {
        User user = query.getCurrentUser(this.mockDFE);
        assertEquals("user should have an id of 1337", new Integer(1337), user.getId());
        assertEquals("user should have a name of unit", "unit", user.getFirstName());
        assertEquals("user should have a lastName of test", "test", user.getLastName());
    }
}
