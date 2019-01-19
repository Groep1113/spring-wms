package seeder;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    private static boolean seedingEnabled = false;

    public DatabaseSeeder() {

    }

    public static void enableSeeding() {
        DatabaseSeeder.seedingEnabled = true;
    }

    public static void disableSeeding() {
        DatabaseSeeder.seedingEnabled = false;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (DatabaseSeeder.seedingEnabled) {
            System.out.println("Seeding is enabled!!");
        } else {
            System.out.println("Seeding doesn't work.");
        }
    }
}
