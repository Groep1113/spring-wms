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
        String[] names = {Account.SUPPLIER, Account.WAREHOUSE, Account.IN_USE, Account.MANUAL, Account.WRITE_OFF};
        Location location = null;
        for (Location testLocation: locationRepository.findAll()){
            location = testLocation;
            break;
        }
        for(String name: names) {
            Account account = new Account();
            account.setName(name);
            if(name.equals(Account.WAREHOUSE)) {
                account.setLocation(location);
            }
            repository.save(account);
        }
    }
}
