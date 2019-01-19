package seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.repository.CrudRepository;
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

    Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    public DatabaseSeeder(UserRepository userRepository,
                          CategoryRepository categoryRepository,
                          ItemRepository itemRepository,
                          LocationRepository locationRepository,
                          SupplierRepository supplierRepository,
                          AccountRepository accountRepository,
                          JdbcTemplate jdbcTemplate) {

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
        this.accountRepository = accountRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    private void seed() {
        TableSeeder.setJdbcTemplate(this.jdbcTemplate);
        new UserSeeder(userRepository).seed();
        new CategorySeeder(categoryRepository).seed();
        new LocationSeeder(locationRepository).seed();
        // Requires Locations
        new AccountSeeder(accountRepository, locationRepository).seed();
        // Requires Categories and Locations
        new CategoryLocationSeeder(categoryRepository, locationRepository).seed();
        new SupplierSeeder(supplierRepository).seed();
        // Requires Categories and Suppliers
        new ItemSeeder(itemRepository, categoryRepository, supplierRepository).seed();

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

    public static String getNFromStringArray(int n, String[] array) {
        Random rand = new Random();
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < n; i++) {
            returnString.append(array[rand.nextInt(array.length)]);
        }
        return returnString.toString();
    }
}
