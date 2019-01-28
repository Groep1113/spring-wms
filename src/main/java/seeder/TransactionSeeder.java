package seeder;

import entity.Account;
import entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import repository.AccountRepository;

import java.time.LocalDate;
import java.util.Optional;


public class TransactionSeeder extends Seeder {
    private AccountRepository accountRepository;

    TransactionSeeder(CrudRepository repository, AccountRepository accountRepository) {
        super(repository, "transaction");
        this.accountRepository = accountRepository;
    }

    @Override
    void seedJob() {
        Optional<Account> fromAccount = accountRepository.findByName(Account.SUPPLIER);
        Optional<Account> toAccount = accountRepository.findByName(Account.WAREHOUSE);
        if(fromAccount.isPresent() && toAccount.isPresent()) {
            for (int i = 0; i < 4; i++) {
                String description = (i == 3) ? "Test transaction, late" : "Test transaction";
                LocalDate now = LocalDate.now();
                LocalDate received = now.minusDays(2 + i);
                LocalDate plannedDate = now.minusDays(1 + i);
                Transaction transaction = new Transaction(fromAccount.get(), toAccount.get(), plannedDate, description);
                if (i < 3) {
                    transaction.setReceivedDate(received);
                }
                repository.save(transaction);
            }
        }
    }
}
