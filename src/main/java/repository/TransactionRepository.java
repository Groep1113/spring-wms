package repository;

import entity.Item;
import entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Iterable<Transaction> findAllByFromAccountNameAndToAccountName(String fromName, String toName);

    default Iterable<Transaction> findAllByFromAccountNameAndToAccountName(Transaction transaction) {
        return findAllByFromAccountNameAndToAccountName(transaction == null ? null : transaction.getFromAccount().getName(), transaction == null ? null : transaction.getToAccount().getName());
    }


    @Query("select t from Transaction t where t.plannedDate > ?1")
    List<Transaction> findByPlannedDateGreaterThan(LocalDate fromDate);


    // Could be with itemId, but would have to assign collumn name on the TransactionLine entity - not sure if  that would break anything.
    @Query("select t from Transaction t inner join TransactionLine tl on t = tl.transaction where t.plannedDate > ?1 and tl.item = ?2 and t.receivedDate = null")
    List<Transaction> findByPlannedDateGreaterThanContainingItemNotReceived(LocalDate fromDate, Item item);

    default List<Transaction> findByPlannedDateGreaterThanContainingItemNotReceived(Transaction transaction, Item item) {
        return findByPlannedDateGreaterThanContainingItemNotReceived(transaction.getPlannedDate(), item);
    }
}
