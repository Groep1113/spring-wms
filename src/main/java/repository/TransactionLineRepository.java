package repository;

import entity.TransactionLine;
import org.springframework.data.repository.CrudRepository;

public interface TransactionLineRepository extends CrudRepository<TransactionLine, Integer> {

    Iterable<TransactionLine> findAllByTransactionId(Integer transactionId);

    default Iterable<TransactionLine> findAllByTransactionId(TransactionLine transactionLine) {
        return findAllByTransactionId(transactionLine == null ? null : transactionLine.getTransaction().getId());
    }

    Iterable<TransactionLine> findAllByItemId(Integer itemId);

    default Iterable<TransactionLine> findAllByItemId(TransactionLine transactionLine) {
        return findAllByItemId(transactionLine == null ? null : transactionLine.getItem().getId());
    }


}
