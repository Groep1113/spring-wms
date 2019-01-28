import entity.Item;
import graphql.Mutation;
import graphql.Query;
import graphql.schema.DataFetchingEnvironment;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ItemTests {
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

    // mutation tests

    @Test
    public void itemShouldBeCreatable() {
        ArrayList<Integer> locationIds = new ArrayList<>();
        locationIds.add(1);
        Item item = mutation.createItem("unitTest", "UT1", 5, locationIds, null, 1, this.mockDFE);
        assertNotNull("created item object should not be null", item);
    }

}
