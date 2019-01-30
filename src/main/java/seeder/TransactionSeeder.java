package seeder;

import entity.Account;
import entity.Location;
import entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import repository.AccountRepository;
import repository.LocationRepository;

import java.time.LocalDate;
import java.util.Optional;


public class TransactionSeeder extends Seeder {
    private LocationRepository locationRepository;
    private AccountRepository accountRepository;

    TransactionSeeder(CrudRepository repository, AccountRepository accountRepository, LocationRepository locationRepository) {
        super(repository, "transaction");
        this.accountRepository = accountRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    void seedJob() {
        Optional<Account> fromAccount = accountRepository.findByName(Account.SUPPLIER);

        if(fromAccount.isPresent()) {
            int i = 0;
            for(Location location: locationRepository.findAll()) {
                Optional<Account> toAccount = accountRepository.findByLocationId(location.getId());
                if(!toAccount.isPresent())
                    continue;
                String description = "Test transaction";
                LocalDate now = LocalDate.now();
                LocalDate received = now.minusDays(2 + i);
                LocalDate plannedDate = now.minusDays(1 + i);
                Transaction transaction = new Transaction(fromAccount.get(), toAccount.get(), plannedDate, description);
                if (i > 1) {
                    transaction.setReceivedDate(received);
                }
                repository.save(transaction);
                i++;
            }
        }
    }
}
