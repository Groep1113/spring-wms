package repository;

import entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Iterable<Transaction> findAllByFromAccountName(String name);

    default Iterable<Transaction> findAllByFromAccountName(Transaction transaction) {
        return findAllByFromAccountName(transaction == null ? null : transaction.getFromAccount().getName());
    }
}
