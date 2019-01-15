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
    private TransactionLineRepository transactionLineRepository;
    private AccountRepository accountRepository;
    private BalanceRepository balanceRepository;
    private AttributeRepository attributeRepository;
    private TransactionMutationRepository transactionMutationRepository;

    @Autowired
    public Query(UserRepository userRepository,
                 RoleRepository roleRepository,
                 SupplierRepository supplierRepository,
                 ItemRepository itemRepository,
                 LocationRepository locationRepository,
                 CategoryRepository categoryRepository,
                 TransactionRepository transactionRepository,
                 TransactionLineRepository transactionLineRepository,
                 AccountRepository accountRepository,
                 BalanceRepository balanceRepository,
                 AttributeRepository attributeRepository,
                 TransactionMutationRepository transactionMutationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.supplierRepository = supplierRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.transactionLineRepository = transactionLineRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.attributeRepository = attributeRepository;
        this.transactionMutationRepository = transactionMutationRepository;
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

    public List<Item> getItems(Boolean showDeleted, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);
        ArrayList<Item> items = new ArrayList<>(((List<Item>) itemRepository.findAll()));

        if (showDeleted == null || !showDeleted) {
            ArrayList<Item> toRemove = new ArrayList<>();
            for (Item item : items)
                if (item.getDeletedDate() != null)
                    toRemove.add(item);
            items.removeAll(toRemove);
        }

        return items;
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

    public List<Transaction> getTransactions(Boolean showDeleted, Boolean showOrders, Boolean showReservations, Boolean showReturns, Boolean showLocations, Boolean showWriteOff, Boolean showManual, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        ArrayList<Transaction> transactions = new ArrayList<>();

        if (showOrders != null && showOrders) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.SUPPLIER, Account.WAREHOUSE));
        }

        if (showReservations != null && showReservations) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.WAREHOUSE, Account.IN_USE));
        }

        if (showReturns != null && showReturns) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.IN_USE, Account.WAREHOUSE));
        }

        if (showLocations != null && showLocations)
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.WAREHOUSE, Account.WAREHOUSE));

        if (showWriteOff != null && showWriteOff)
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.WAREHOUSE, Account.WRITE_OFF));

        if (showManual != null && showManual) {
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.WAREHOUSE, Account.MANUAL));
            transactions.addAll((List<Transaction>) transactionRepository.findAllByFromAccountNameAndToAccountName(Account.MANUAL, Account.WAREHOUSE));
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

    public List<Account> getAccounts(Boolean showDeleted, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        ArrayList<Account> accounts = new ArrayList<>(((List<Account>) accountRepository.findAll()));

        if (showDeleted == null || !showDeleted) {
            ArrayList<Account> toRemove = new ArrayList<>();
            for (Account account : accounts) if (account.getDeletedDate() != null) toRemove.add(account);
            accounts.removeAll(toRemove);
        }

        return accounts;
    }

    public Balance getBalance(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return balanceRepository.findById(id).orElse(null);
    }

    public List<Balance> getBalances(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Balance>) balanceRepository.findAll());
    }

    public TransactionLine getTransactionLine(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return transactionLineRepository.findById(id).orElse(null);
    }

    public List<TransactionLine> getTransactionLines(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<TransactionLine>) transactionLineRepository.findAll());
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

    public TransactionMutation getTransactionMutation(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return transactionMutationRepository.findById(id).orElse(null);
    }

    public List<TransactionMutation> getTransactionMutations(Integer transactionId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        if (transactionId != null){
            return ((List<TransactionMutation>) transactionMutationRepository.findAllByTransactionId(transactionId));
        } else
            return ((List<TransactionMutation>) transactionMutationRepository.findAll());
    }
}