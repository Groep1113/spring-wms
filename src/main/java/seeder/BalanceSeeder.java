package seeder;

import entity.Balance;
import entity.Transaction;
import entity.TransactionLine;
import org.springframework.data.repository.CrudRepository;
import repository.AccountRepository;
import repository.BalanceRepository;
import repository.TransactionRepository;

import java.util.Optional;

public class BalanceSeeder extends Seeder{
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;

    BalanceSeeder(BalanceRepository repository,
                  TransactionRepository transactionRepository) {
        super(repository, "balance");
        this.balanceRepository = repository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    void seedJob() {
        for (Transaction transaction: transactionRepository.findAll()) {
            if (transaction.getReceivedDate() != null) {
                for (TransactionLine tl : transaction.getTransactionLines()) {
                    Optional<Balance> fromBalancePromise = balanceRepository.findByAccountAndItem(transaction.getFromAccount(), tl.getItem());
                    Optional<Balance> toBalancePromise = balanceRepository.findByAccountAndItem(transaction.getToAccount(), tl.getItem());
                    Balance fromBalance;
                    Balance toBalance;

                    if (!fromBalancePromise.isPresent()) {
                        fromBalance = new Balance();
                        fromBalance.setAccount(transaction.getFromAccount());
                        fromBalance.setItem(tl.getItem());
                        fromBalance.setAmount(0);
                    } else {
                        fromBalance = fromBalancePromise.get();
                    }

                    if (!toBalancePromise.isPresent()) {
                        toBalance = new Balance();
                        toBalance.setAccount(transaction.getToAccount());
                        toBalance.setItem(tl.getItem());
                        toBalance.setAmount(0);
                    } else {
                        toBalance = toBalancePromise.get();
                    }
                    toBalance.setAmount(toBalance.getAmount() + tl.getAmount());
                    fromBalance.setAmount(fromBalance.getAmount() - tl.getAmount());
                    balanceRepository.save(toBalance);
                    balanceRepository.save(fromBalance);
                }
            }
        }
    }
}
