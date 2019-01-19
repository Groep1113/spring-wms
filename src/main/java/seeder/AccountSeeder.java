package seeder;

import entity.Account;
import entity.Location;
import org.springframework.data.repository.CrudRepository;
import repository.LocationRepository;

public class AccountSeeder extends TableSeeder{
    private final LocationRepository locationRepository;

    AccountSeeder(CrudRepository repository, LocationRepository locationRepository) {
        super(repository, "account");
        this.locationRepository = locationRepository;
    }

    @Override
    void seedJob() {
        String[] names = {Account.SUPPLIER, Account.WAREHOUSE, Account.IN_USE, Account.MANUAL, Account.WRITE_OFF};
        // TODO: Remove / Fix locations. What are locations for in context of accounts?
        Location location = null;
        for(Location testLocation: locationRepository.findAll()){
            location = testLocation;
            break;
        }
        for(String name: names) {
            Account account = new Account();
            account.setName(name);
            account.setLocation(location);
            repository.save(account);
        }
    }
}
