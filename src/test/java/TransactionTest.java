import entity.Role;
import entity.Transaction;
import entity.User;
import graphql.AuthContext;
import graphql.GraphQLException;
import graphql.Mutation;
import graphql.Query;
import graphql.schema.DataFetchingEnvironment;
import mocks.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class TransactionTest {

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
                new TransactionLineRepo(),
                new AccountRepo(),
                new BalanceRepo(),
                new AttributeRepo(),
                new TransactionMutationRepo()
        );
        this.query = new Query(
                new UserRepo(),
                new RoleRepo(),
                new SupplierRepo(),
                new ItemRepo(),
                new LocationRepo(),
                new CategoryRepo(),
                new TransactionRepo(),
                new TransactionLineRepo(),
                new AccountRepo(),
                new BalanceRepo(),
                new AttributeRepo(),
                new TransactionMutationRepo()
        );
        this.mockDFE = new MockDFE();
    }

    @Test
    public void canCreateEmptyReservation() {
        Transaction transaction = mutation.createReservationTransaction(null, null, null, null, null, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canCreateReservation() {
        Integer itemId = 1;
        Integer amount = 1;
        LocalDate plannedDate = LocalDate.now();
        String description = "description";
        Integer locationId = 1;
        Transaction transaction = mutation.createReservationTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test(expected = GraphQLException.class)
    public void canCreateOrderWithoutAdminAuth() {
        Integer itemId = 1;
        Integer amount = 1;
        LocalDate plannedDate = LocalDate.now();
        String description = "description";
        Integer locationId = 1;
        mutation.createOrderTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
    }


    @Test
    public void canCreateOrderWithAdminAuth() {
        User user = ((AuthContext) mockDFE.getContext()).getUser();
        Role role = new Role();
        role.setName(Role.ADMIN);
        user.getRoles().add(role);
        Integer itemId = 1;
        Integer amount = 1;
        LocalDate plannedDate = LocalDate.now();
        String description = "description";
        Integer locationId = 1;
        Transaction transaction = mutation.createOrderTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }
}
