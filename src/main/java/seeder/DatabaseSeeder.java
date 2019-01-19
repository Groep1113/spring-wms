package seeder;

import entity.Account;
import entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import repository.*;

import java.util.Random;

@Component
public class DatabaseSeeder {
    private static boolean seedingEnabled = false;
    private final JdbcTemplate jdbcTemplate;
    private final SupplierRepository supplierRepository;
    private final LocationRepository locationRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionLineRepository transactionLineRepository;

    Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);


    @Autowired
    public DatabaseSeeder(UserRepository userRepository,
                          CategoryRepository categoryRepository,
                          ItemRepository itemRepository,
                          LocationRepository locationRepository,
                          SupplierRepository supplierRepository,
                          AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          TransactionLineRepository transactionLineRepository,
                          JdbcTemplate jdbcTemplate) {

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionLineRepository = transactionLineRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    private void seed() {
        Seeder.setJdbcTemplate(this.jdbcTemplate);

        // NOTE: Order is important!
        new UserSeeder(userRepository).seed();
        new CategorySeeder(categoryRepository).seed();
        new LocationSeeder(locationRepository).seed();
        new AccountSeeder(accountRepository, locationRepository).seed();
        new CategoryLocationSeeder(categoryRepository, locationRepository).seed();
        new SupplierSeeder(supplierRepository).seed();
        new ItemSeeder(itemRepository, categoryRepository, supplierRepository).seed();
        new TransactionSeeder(transactionRepository, accountRepository).seed();
        new TransactionLineSeeder(transactionLineRepository, transactionRepository, itemRepository).seed();

    }

    public static void enableSeeding() {
        DatabaseSeeder.seedingEnabled = true;
    }

    public static void disableSeeding() {
        DatabaseSeeder.seedingEnabled = false;
    }

    @EventListener
    public void checkSeedOnEvent(ContextRefreshedEvent event) {
        if (DatabaseSeeder.seedingEnabled) {
            logger.info("Seed jobs sent.");
            seed();
        }
    }

    static String getNFromStringArray(int n, String[] array) {
        Random rand = new Random();
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < n; i++) {
            returnString.append(array[rand.nextInt(array.length)]);
        }
        return returnString.toString();
    }
}
