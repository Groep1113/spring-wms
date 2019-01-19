package seeder;

import entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Random;

public class UserSeeder {
    private CrudRepository repository;
    public UserSeeder(CrudRepository repository) {
        this.repository = repository;
    }

    public void seed() {
        String[] firstNames = {"Bob", "Henk", "Klaas", "Piet", "Froukje", "Linda"};
        String[] surnames = {"de Vries", "Klaassen", "Teunissen", "Boxma", "de Wilg", "Vredeveld"};
        Random rand = new Random();
        for(int i = 0; i < 5; i++) {
            User u = new User();
            String firstName = firstNames[rand.nextInt(firstNames.length - 1)];
            u.setFirstName(firstName);
            u.setLastName(surnames[rand.nextInt(surnames.length - 1)]);
            u.setEmail(firstName + "@bs-htg.com");
            u.setPassword("password");
            this.repository.save(u);
        }

    }
}
