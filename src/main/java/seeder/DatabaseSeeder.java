package seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import repository.UserRepository;

@Component
public class DatabaseSeeder {
    private static boolean seedingEnabled = false;
    private final JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;

    @Autowired
    public DatabaseSeeder(UserRepository userRepository,
                          JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    private void seed() {
        TableSeeder.setJdbcTemplate(this.jdbcTemplate);
        new UserSeeder(this.userRepository).seed();
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
            System.out.println("Seed jobs sent.");
            seed();
        }
    }
}
