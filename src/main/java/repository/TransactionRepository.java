package repository;

import entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Iterable<Transaction> findAllByFromAccountNameAndToAccountName(String fromName, String toName);

    default Iterable<Transaction> findAllByFromAccountNameAndToAccountName(Transaction transaction) {
        return findAllByFromAccountNameAndToAccountName(transaction == null ? null : transaction.getFromAccount().getName(), transaction == null ? null : transaction.getToAccount().getName());
    }
}
