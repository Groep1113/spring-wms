package graphql;

import base.TokenManager;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import entity.*;
import graphql.schema.DataFetchingEnvironment;
import graphql.types.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unused")
@Component
public class Mutation implements GraphQLMutationResolver {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private SupplierRepository supplierRepository;
    private LocationRepository locationRepository;
    private CategoryRepository categoryRepository;
    private RoleRepository roleRepository;
    private TransactionRepository transactionRepository;
    private TransactionLineRepository transactionLineRepository;
    private AccountRepository accountRepository;
    private BalanceRepository balanceRepository;
    private AttributeRepository attributeRepository;

    @Autowired
    public Mutation(
        UserRepository userRepository,
        ItemRepository itemRepository,
        SupplierRepository supplierRepository,
        LocationRepository locationRepository,
        CategoryRepository categoryRepository,
        RoleRepository roleRepository,
        TransactionRepository transactionRepository,
        TransactionLineRepository transactionLineRepository,
        AccountRepository accountRepository,
        BalanceRepository balanceRepository,
        AttributeRepository attributeRepository
    ) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.supplierRepository = supplierRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.transactionLineRepository = transactionLineRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.attributeRepository = attributeRepository;
    }

    private String idNotFoundMessage(int id, String entity) {
        return "No " + entity.toLowerCase() + " found with id " + id + ".";
    }

    private void throwIdNotFound(int id, String className) {
        throw new GraphQLException(idNotFoundMessage(id, className));
    }

    public LoginPayload login(String email, String password) {
        User user = userRepository
            .authenticate(email, password)
            .orElse(null);
        if (user == null) throw new GraphQLException("Invalid login");

        String token = TokenManager.generateToken();
        user.setToken(token);
        userRepository.save(user);

        return new LoginPayload(token, user);
    }

    public Item createItem(String name, String code, Integer recommendedStock, Integer locationId, Integer categoryId, Integer supplierId, DataFetchingEnvironment
        env) {
        AuthContext.requireAuth(env);

        Location location = locationId == null ? null : locationRepository
                .findById(locationId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())));

        Category category = categoryId == null ? null : categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Supplier supplier = supplierId == null ? null : supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(supplierId, Supplier.class.getSimpleName())));

        return itemRepository.save(new Item(
            name,
            code,
            recommendedStock,
            location,
            category,
            supplier
        ));
    }

    public Item updateItem(Integer itemId, String name, String code, Integer recommendedStock, Integer supplierId) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        if (name != null) item.setName(name);
        if (code != null) item.setCode(code);
        if (recommendedStock != null) item.setRecommendedStock(recommendedStock);
        if (supplierId != null) item.setSupplier(supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(supplierId, Supplier.class.getSimpleName()))));

        return itemRepository.save(item);
    }

    public Location createLocation(String code, int depth, int width, int height, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return locationRepository.save(new Location(code, depth, width, height));
    }

    public Category createCategory(String name, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return categoryRepository.save(new Category(name));
    }

    public Item itemAddLocation(int itemId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));
        Set<Location> locations = item.getLocations();

        Location location = locationRepository
            .findById(locationId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())));

        locations.add(location);
        return itemRepository.save(item);
    }

    public Item itemRemoveLocation(int itemId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));
        Set<Location> locations = item.getLocations();

        Location location = null;

        for (Location loc: locations) {
            if (loc.getId() == locationId) location = loc;
        }

        if (location != null) locations.remove(location);
        else { throw new GraphQLException("Item does not belong to this location."); }
        return itemRepository.save(item);
    }

    public Item itemAddCategory(int itemId, int categoryId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        Category category = categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Set<Category> categories = item.getCategories();
        for (Category cat : categories) {
            if (cat.getId() == categoryId) {
                throw new GraphQLException("Item (id:" + itemId + ") is already added to this category (id:" + categoryId + ").");
            }
        }
        categories.add(category);
        return itemRepository.save(item);
    }

    public Item itemRemoveCategory(int itemId, int categoryId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        Set<Category> categories = item.getCategories();
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                categories.remove(category);
                return itemRepository.save(item);
            }
        }
        throw new GraphQLException("This item (id:" + itemId + ") is not part of this category (id:" + categoryId + "). Therefore, it can not be removed from it.");
    }

    public Item createAttribute(int itemId, String name, String value, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        attributeRepository.save(new Attribute(name, value, item));

        return item;
    }

    public Boolean deleteAttribute(int attributeId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        attributeRepository.deleteById(attributeId);

        return true;
    }

    public Attribute updateAttribute(int attributeId, String name, String value, Integer itemId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Attribute attribute = attributeRepository
                .findById(attributeId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(attributeId, Attribute.class.getSimpleName())));

        if (name != null) attribute.setName(name);
        if (value != null) attribute.setValue(value);
        if (itemId != null) {
            attribute.setItem(itemRepository
                    .findById(itemId)
                    .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName()))));
        }

        return attributeRepository.save(attribute);
    }

    public Category categoryChangeName(int id, String name, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
            .findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Category.class.getSimpleName())));

        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category categoryAddLocation(int categoryId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Location location = locationRepository
            .findById(locationId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())));

        Set<Location> locations = category.getLocations();
        for (Location loc : locations) {
            if (loc.getId() == locationId) {
                throw new GraphQLException("Location (id:" + locationId + ") is already added to this category (id:" + categoryId + ").");
            }
        }
        locations.add(location);
        return categoryRepository.save(category);
    }

    public Category categoryRemoveLocation(int categoryId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Set<Location> locations = category.getLocations();
        for (Location location : locations) {
            if (location.getId() == locationId) {
                locations.remove(location);
                return categoryRepository.save(category);
            }
        }
        throw new GraphQLException("This location (id:" + locationId + ") is not part of this category (id:" + categoryId + "). Therefore, it can not be removed from it.");
    }

    public Boolean deleteItem(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        if (transactionLineRepository.findAllByItemId(id).iterator().hasNext())
            throw new GraphQLException("Cant delete item, because it is being referenced by transaction");
        
        itemRepository.delete(itemRepository
            .findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Item.class.getSimpleName()))));

        return true;
    }

    public Boolean deleteLocation(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        locationRepository.delete(locationRepository
            .findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Location.class.getSimpleName()))));
        return true;
    }

    public Boolean deleteCategory(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        categoryRepository.delete(categoryRepository
            .findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Category.class.getSimpleName()))));
        return true;
    }

    public User createUser(
        String firstName, String lastName, String email, String password, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public User addRole(int userId, int roleId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Optional<User> optUser = userRepository.findById(userId);
        if (!optUser.isPresent()) throwIdNotFound(userId, User.class.getSimpleName());
        Optional<Role> optRole = roleRepository.findById(roleId);
        if (!optRole.isPresent()) throwIdNotFound(roleId, Role.class.getSimpleName());

        User user = optUser.get();
        Role role = optRole.get();
        user.addRole(role);
        return userRepository.save(user);
    }

    public User removeRole(int userId, int roleId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Optional<User> optUser = userRepository.findById(userId);
        if (!optUser.isPresent()) throwIdNotFound(userId, User.class.getSimpleName());
        Optional<Role> optRole = roleRepository.findById(roleId);
        if (!optRole.isPresent()) throwIdNotFound(roleId, Role.class.getSimpleName());

        User user = optUser.get();
        Role role = optRole.get();
        user.removeRole(role);

        return userRepository.save(user);
    }

    public Account createWarehouseAccount(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return createAccount(Account.WAREHOUSE);
    }

    public Account createInUseAccount(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return createAccount(Account.IN_USE);
    }

    public Account createSupplierAccount(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return createAccount(Account.IN_USE);
    }

    private Account createAccount(String name) {
        Optional<Account> result = accountRepository.findByName(name);
        if (result.isPresent()) throw new GraphQLException("Account " + name + " already exists.");
        return accountRepository.save(new Account(name));
    }

    public Balance createBalance(int itemId, Integer accountId, Integer amount, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);


        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        Account account;
        if (accountId == null)
            account = accountRepository
                .findByName(Account.WAREHOUSE)
                .orElseGet(() -> accountRepository.save(new Account(Account.WAREHOUSE)));
        else
            account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(accountId, Account.class.getSimpleName())));

        if (balanceRepository.findByAccountAndItem(account, item).isPresent())
            throw new GraphQLException("A balance with account (" + account.getName() + ") and item " + item.getName() + " already exists");

        return balanceRepository.save(new Balance(account, item, amount == null ? 0 : amount));
    }

    public Balance setBalance(Integer balanceId, Integer amount, String reason, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Balance balance = balanceRepository
            .findById(balanceId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(balanceId, Balance.class.getSimpleName())));

        int mutationAmount;
        if (amount < balance.getAmount())
            mutationAmount = balance.getAmount() - amount;
        else
            mutationAmount = amount - balance.getAmount();

        balance.setAmount(amount);

        return balance;
    }

    public Transaction createReservationTransaction(
        Integer itemId, Integer amount, LocalDate plannedDate, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        Account fromAccount = accountRepository
            .findByName(Account.WAREHOUSE)
            .orElseGet(() -> accountRepository.save(new Account(Account.WAREHOUSE)));
        Account toAccount = accountRepository
            .findByName(Account.IN_USE)
            .orElseGet(() -> accountRepository.save(new Account(Account.IN_USE)));


        if (itemId != null && amount != null)
            return createTransactionWithLine(itemId, amount, plannedDate, env, fromAccount, toAccount);

        return transactionRepository.save(new Transaction(fromAccount, toAccount, plannedDate));
    }
    
    public Transaction createOrderTransaction(Integer itemId, Integer amount, LocalDate plannedDate, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);
    
        Account fromAccount = accountRepository
                .findByName(Account.SUPPLIER)
                .orElseGet(() -> accountRepository.save(new Account(Account.SUPPLIER)));
        Account toAccount = accountRepository
                .findByName(Account.WAREHOUSE)
                .orElseGet(() -> accountRepository.save(new Account(Account.WAREHOUSE)));
    
    
        if (itemId != null && amount != null)
            return createTransactionWithLine(itemId, amount, plannedDate, env, fromAccount, toAccount);
    
        return transactionRepository.save(new Transaction(fromAccount, toAccount, plannedDate));
    }

    
    public Transaction createReturnTransaction(
        Integer itemId, Integer amount, LocalDate plannedDate, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        Account fromAccount = accountRepository
            .findByName(Account.IN_USE)
            .orElseGet(() -> accountRepository.save(new Account(Account.IN_USE)));
        Account toAccount = accountRepository
            .findByName(Account.WAREHOUSE)
            .orElseGet(() -> accountRepository.save(new Account(Account.WAREHOUSE)));

        if (itemId != null && amount != null)
            return createTransactionWithLine(itemId, amount, plannedDate, env, fromAccount, toAccount);

        return transactionRepository.save(new Transaction(fromAccount, toAccount, plannedDate));
    }

    private Transaction createTransactionWithLine(
        Integer itemId, Integer amount, LocalDate plannedDate, DataFetchingEnvironment env,
        Account fromAccount, Account toAccount) {
        Transaction transaction = transactionRepository.save(new Transaction(fromAccount, toAccount, plannedDate));
        addLineToTransaction(transaction.getId(), itemId, amount, env);

        return transaction;
    }

    public Transaction deleteTransaction(Integer transactionId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Transaction transaction = transactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(transactionId, Transaction.class.getSimpleName())));

        if (transaction.getDeletedDate() != null)
            throw new GraphQLException("This transaction was already deleted on " + transaction.getDeletedDate() + ".");
        transaction.setDeletedDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    //TODO rename to update
    public Transaction changeTransaction(
        Integer transactionId, Integer fromAccountId, Integer toAccountId, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        Transaction transaction = transactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(transactionId, Transaction.class.getSimpleName())));

        if (fromAccountId != null)
            transaction.setFromAccount(accountRepository.findById(fromAccountId).orElseThrow(() -> new GraphQLException(idNotFoundMessage(fromAccountId, Account.class.getSimpleName()))));

        if (toAccountId != null)
            transaction.setToAccount(accountRepository.findById(toAccountId).orElseThrow(() -> new GraphQLException(idNotFoundMessage(toAccountId, Account.class.getSimpleName()))));

        return transactionRepository.save(transaction);
    }

    public TransactionLine addLineToTransaction(
        Integer transactionId, Integer itemId, Integer amount, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        Transaction transaction = transactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(transactionId, Transaction.class.getSimpleName())));

        if (transaction.getLocked()) throw new GraphQLException("This transaction is locked.");

        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        return transactionLineRepository.save(new TransactionLine(amount, transaction, item));
    }


    //TODO rename to update
    public TransactionLine changeTransactionLine(
        Integer transactionLineId, Integer itemId, Integer amount, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        TransactionLine transactionLine = transactionLineRepository
            .findById(transactionLineId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(transactionLineId, TransactionLine.class.getSimpleName())));

        if (transactionLine.getTransaction().getLocked())
            throw new GraphQLException("The transaction linked to this line is locked, and therefore, can not be changed.");

        if (itemId != null)
            transactionLine.setItem(itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName()))));

        if (amount != null)
            transactionLine.setAmount(amount);

        return transactionLineRepository.save(transactionLine);
    }

    public Boolean deleteTransactionLine(Integer transactionLineId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        transactionLineRepository.deleteById(transactionLineId);
        return true;
    }

    private void safeTransactionCheck(Transaction transaction) {
        Iterable<TransactionLine> transactionLines = transactionLineRepository.findAllByTransactionId(transaction.getId());

        for (TransactionLine transactionLine :
            transactionLines) {
            Item item = transactionLine.getItem();
            Account fromAccount = transaction.getFromAccount();
            if (fromAccount.getName().equals(Account.WAREHOUSE)) {
                Balance balance = balanceRepository
                        .findByAccountAndItem(fromAccount, item)
                        .orElseThrow(() -> noStockDefined(fromAccount, item));
                if (balance.getAmount() < transactionLine.getAmount()) throw new GraphQLException("Not enough stock for item");
            }
        }
    }

    private GraphQLException noStockDefined(Account account, Item item) {
        return new GraphQLException("No stock defined for item " + item.getName() + " at " + account.getName() + ".");
    }

    private void processBalanceChanges(Transaction transaction) {
        Iterable<TransactionLine> transactionLines = transactionLineRepository.findAllByTransactionId(transaction.getId());
        for (TransactionLine transactionLine :
            transactionLines) {

            Item item = transactionLine.getItem();
            Account fromAccount = transaction.getFromAccount();
            Account toAccount = transaction.getToAccount();

            if (fromAccount.getName().equals(Account.WAREHOUSE)) {
                Balance fromBalance = balanceRepository
                    .findByAccountAndItem(fromAccount, item)
                    .orElseThrow(() -> noStockDefined(fromAccount, item));

                fromBalance.setAmount(fromBalance.getAmount() + transactionLine.getAmount() * -1);
                balanceRepository.save(fromBalance);
            }

            if (toAccount.getName().equals(Account.WAREHOUSE)) {
                Balance toBalance = balanceRepository
                    .findByAccountAndItem(toAccount, item)
                    .orElseThrow(() -> noStockDefined(toAccount, item));

                toBalance.setAmount(toBalance.getAmount() + transactionLine.getAmount());
                balanceRepository.save(toBalance);
            }

            transaction.setReceivedDate(LocalDate.now());
            transactionLineRepository.save(transactionLine);
        }
    }

    public Transaction executeTransaction(int transactionId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Transaction transaction = transactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(transactionId, Transaction.class.getSimpleName())));

        if (transaction.getDeletedDate() != null)
            throw new GraphQLException("This transaction has been deleted, and therefore, can not be executed.");

        safeTransactionCheck(transaction);
        processBalanceChanges(transaction);

        transaction.setReceivedDate(LocalDate.now());
        transaction.setLocked(true);
        return transactionRepository.save(transaction);
    }

    public User updateUser(
        Integer id, String firstName, String lastName, String email, String password, DataFetchingEnvironment env
    ) {
        AuthContext.requireAuth(env);

        User user = userRepository.findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, User.class.getSimpleName())));

        if (!firstName.isEmpty()) user.setFirstName(firstName);
        if (!lastName.isEmpty()) user.setLastName(lastName);
        if (!email.isEmpty()) user.setEmail(email);
        if (!password.isEmpty()) user.setPassword(password);

        return userRepository.save(user);
    }

    public Supplier createSupplier(String name, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Supplier supplier = new Supplier();
        supplier.setName(name);

        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(String name, Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Supplier.class.getSimpleName())));
        supplier.setName(name);

        return supplierRepository.save(supplier);
    }

    public Supplier deleteSupplier(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Supplier.class.getSimpleName())));

        supplierRepository.delete(supplier);
        return supplier;
    }
}

