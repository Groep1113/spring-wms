package seeder;

import entity.Account;
import entity.Location;
import org.springframework.data.repository.CrudRepository;
import repository.LocationRepository;

public class AccountSeeder extends Seeder {
    private final LocationRepository locationRepository;

    AccountSeeder(CrudRepository repository, LocationRepository locationRepository) {
        super(repository, "account");
        this.locationRepository = locationRepository;
    }

    @Override
    void seedJob() {
        String[] names = {Account.SUPPLIER, Account.IN_USE, Account.MANUAL, Account.WRITE_OFF};
        Location location = null;
        for (Location testLocation: locationRepository.findAll()){
            location = testLocation;
            break;
        }
        for(String name: names) {
            Account account = new Account();
            account.setName(name);
            repository.save(account);
        }
        for (Location loc: locationRepository.findAll()){
            Account account = new Account();
            account.setName(Account.WAREHOUSE);
            account.setLocation(loc);
            repository.save(account);
        }
    }
}
