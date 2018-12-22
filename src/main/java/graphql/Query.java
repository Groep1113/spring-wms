package graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import entity.*;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.*;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;
    private CategoryRepository categoryRepository;
    private TransactionRepository transactionRepository;
    private TransactionRuleRepository transactionRuleRepository;
    private AccountRepository accountRepository;
    private BalanceRepository balanceRepository;
    private BalanceMutationRepository balanceMutationRepository;

    @Autowired
    public Query(UserRepository userRepository,
                 ItemRepository itemRepository,
                 LocationRepository locationRepository,
                 CategoryRepository categoryRepository,
                 TransactionRepository transactionRepository,
                 TransactionRuleRepository transactionRuleRepository,
                 AccountRepository accountRepository,
                 BalanceRepository balanceRepository,
                 BalanceMutationRepository balanceMutationRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.transactionRuleRepository = transactionRuleRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.balanceMutationRepository = balanceMutationRepository;
    }



    // These method names have to line up with the schema.graphqls field definitions
    // (these are the get methods for our graphql schema)

    public List<User> getUsers(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<User>) userRepository.findAll());
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

    public List<Transaction> getTransactions(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Transaction>) transactionRepository.findAll());
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

}