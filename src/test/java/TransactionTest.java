import entity.Account;
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
import java.util.ArrayList;

public class TransactionTest {

    private Mutation mutation;
    private Query query;
    private DataFetchingEnvironment mockDFE;

    private Integer itemId = 1;
    private Integer amount = 1;
    private LocalDate plannedDate = LocalDate.now();
    private String description = "description";
    private Integer locationId = 1;

    private UserRepo                userRepo;
    private ItemRepo                itemRepo;
    private SupplierRepo            supplierRepo;
    private LocationRepo            locationRepo;
    private CategoryRepo            categoryRepo;
    private RoleRepo                roleRepo;
    private TransactionRepo         transactionRepo;
    private TransactionLineRepo     transactionLineRepo;
    private AccountRepo             accountRepo;
    private BalanceRepo             balanceRepo;
    private AttributeRepo           attributeRepo;
    private TransactionMutationRepo transactionMutationRepo;

    @Before
    public void setup() {
        userRepo = new UserRepo();
        itemRepo = new ItemRepo();
        supplierRepo = new SupplierRepo();
        locationRepo = new LocationRepo();
        categoryRepo = new CategoryRepo();
        roleRepo = new RoleRepo();
        transactionRepo = new TransactionRepo();
        transactionLineRepo = new TransactionLineRepo();
        accountRepo = new AccountRepo();
        balanceRepo = new BalanceRepo();
        attributeRepo = new AttributeRepo();
        transactionMutationRepo =new TransactionMutationRepo() ;


        this.mutation = new Mutation(
                userRepo,
                itemRepo,
                supplierRepo,
                locationRepo,
                categoryRepo,
                roleRepo,
                transactionRepo,
                transactionLineRepo,
                accountRepo,
                balanceRepo,
                attributeRepo,
                transactionMutationRepo
        );
        this.query = new Query(
                userRepo,
                roleRepo,
                supplierRepo,
                itemRepo,
                locationRepo,
                categoryRepo,
                transactionRepo,
                transactionLineRepo,
                accountRepo,
                balanceRepo,
                attributeRepo,
                transactionMutationRepo
        );
        this.mockDFE = new MockDFE();

        this.itemId = 1;
        this.amount = 1;
        this.plannedDate = LocalDate.now();
        this.description = "description";
        this.locationId = 1;
    }

    @Test
    public void canCreateEmptyReservation() {
        Transaction transaction = mutation.createReservationTransaction(null, null, null, null, null, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canCreateReservation() {
        Transaction transaction = mutation.createReservationTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test(expected = GraphQLException.class)
    public void cantCreateOrderWithoutAdminAuth() {
        mutation.createOrderTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
    }

    @Test
    public void canCreateOrderWithAdminAuth() {
        User user = ((AuthContext) mockDFE.getContext()).getUser();
        Role role = new Role();
        role.setName(Role.ADMIN);
        user.getRoles().add(role);
        Transaction transaction = mutation.createOrderTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canCreateReturn() {
        Transaction transaction = mutation.createReturnTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canCreateLocation() {
        Integer fromLoc = 1;
        Integer toLoc = 2;
        Transaction transaction = mutation.createLocationTransaction(itemId, amount, plannedDate, description, fromLoc, toLoc, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canCreateWriteOff() {
        Transaction transaction = mutation.createWriteOffTransaction(itemId, amount, plannedDate, description, locationId, this.mockDFE);
        assertNotNull("created reservation should not be null", transaction);
    }

    @Test
    public void canSeeDeletedQueryWithShowDeletedParameter() {
        Transaction transaction = new Transaction(new Account(Account.WAREHOUSE), new Account(Account.IN_USE), null, description);
        transaction.setDeletedDate(LocalDate.now());
        transactionRepo.setTransaction(transaction);
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) query.getTransactions(true, null, true, null, null, null, null, mockDFE);
        ArrayList<Transaction> expected = new ArrayList<>();
        expected.add(transaction);
        assertEquals(expected, transactions);
    }

    @Test
    public void canNotSeeDeletedQueryWithoutShowDeletedParameter() {
        Transaction transaction = new Transaction(new Account(Account.WAREHOUSE), new Account(Account.IN_USE), null, description);
        transaction.setDeletedDate(LocalDate.now());
        transactionRepo.setTransaction(transaction);
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) query.getTransactions(false, null, true, null, null, null, null, mockDFE);
        assertEquals(new ArrayList<>(), transactions);
    }
}
