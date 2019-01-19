package seeder;

import entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Random;

public class UserSeeder extends TableSeeder{

    public UserSeeder(CrudRepository repository) {
        super(repository, "user");
    }

    @Override
    public void seedJob() {
        String[] firstNames = {"Bob", "Henk", "Klaas", "Piet", "Froukje", "Linda"};
        String[] surnames = {"de Vries", "Klaassen", "Teunissen", "Boxma", "de Wilg", "Vredeveld"};
        Random rand = new Random();
        for(int i = 0; i < 5; i++) {
            User u = new User();
            String firstName = firstNames[rand.nextInt(firstNames.length)];
            u.setFirstName(firstName);
            u.setLastName(surnames[rand.nextInt(surnames.length)]);
            u.setEmail(firstName.toLowerCase()   + "@bs-htg.com");
            u.setPassword("password");
            this.repository.save(u);
        }
    }
}
