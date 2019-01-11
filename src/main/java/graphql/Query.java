package graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import entity.*;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class Query implements GraphQLQueryResolver {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SupplierRepository supplierRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;
    private CategoryRepository categoryRepository;
    private TransactionRepository transactionRepository;
    private TransactionRuleRepository transactionRuleRepository;
    private AccountRepository accountRepository;
    private BalanceRepository balanceRepository;
    private BalanceMutationRepository balanceMutationRepository;
    private AttributeRepository attributeRepository;

    @Autowired
    public Query(UserRepository userRepository,
                 RoleRepository roleRepository,
                 SupplierRepository supplierRepository,
                 ItemRepository itemRepository,
                 LocationRepository locationRepository,
                 CategoryRepository categoryRepository,
                 TransactionRepository transactionRepository,
                 TransactionRuleRepository transactionRuleRepository,
                 AccountRepository accountRepository,
                 BalanceRepository balanceRepository,
                 BalanceMutationRepository balanceMutationRepository,
                 AttributeRepository attributeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.supplierRepository = supplierRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.transactionRuleRepository = transactionRuleRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.balanceMutationRepository = balanceMutationRepository;
        this.attributeRepository = attributeRepository;
    }

    // These method names have to line up with the schema.graphqls field definitions
    // (these are the get methods for our graphql schema)

    public List<User> getUsers(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<User>) userRepository.findAll());
    }

    public User getCurrentUser(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((AuthContext) env.getContext()).getUser();
    }

    public User getUserByMail(String email, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return userRepository
            .findByEmail(email)
            .orElse(null);
    }

    public User getUser(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return userRepository
            .findById(id)
            .orElse(null);
    }

    public Role getRole(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> getRoles(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Role>) roleRepository.findAll());
    }

    public List<Item> getItems(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Item>) itemRepository.findAll());
    }

    public Item getItem(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return itemRepository.findById(id).orElse(null);
    }

    public List<Location> getLocations(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Location>) locationRepository.findAll());
    }

    public Location getLocation(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return locationRepository.findById(id).orElse(null);
    }

    public Category getCategory(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getCategories(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Category>) categoryRepository.findAll());
    }

    public Transaction getTransaction(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> getTransactions(Boolean showDeleted, Boolean showOrders, Boolean showReservations, Boolean showReturns, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        ArrayList<Transaction> transactions = new ArrayList<>();

        if (showOrders != null && showOrders) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountName(Account.SUPPLIER));
        }

        if (showReservations != null && showReservations) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountName(Account.WAREHOUSE));
        }

        if (showReturns != null && showReturns) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountName(Account.IN_USE));
        }

        if (showDeleted == null || !showDeleted) {
            ArrayList<Transaction> toRemove = new ArrayList<>();
            for (Transaction transaction : transactions) if (transaction.getDeletedDate() != null) toRemove.add(transaction);
            transactions.removeAll(toRemove);
        }

        return transactions;
    }

    public Account getAccount(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccounts(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Account>) accountRepository.findAll());
    }

    public Balance getBalance(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return balanceRepository.findById(id).orElse(null);
    }

    public List<Balance> getBalances(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Balance>) balanceRepository.findAll());
    }

    public BalanceMutation getBalanceMutation(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return balanceMutationRepository.findById(id).orElse(null);
    }

    public List<BalanceMutation> getBalanceMutations(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<BalanceMutation>) balanceMutationRepository.findAll());
    }

    public TransactionRule getTransactionRule(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return transactionRuleRepository.findById(id).orElse(null);
    }

    public List<TransactionRule> getTransactionRules(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<TransactionRule>) transactionRuleRepository.findAll());
    }

    public List<Supplier> getSuppliers(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Supplier>) supplierRepository.findAll());
    }

    public Supplier getSupplier(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return supplierRepository.findById(id).orElse(null);
    }

    public Attribute getAttribute(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return attributeRepository.findById(id).orElse(null);
    }

    public List<Attribute> getAttributes(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Attribute>) attributeRepository.findAll());
    }
}