package repository;

import entity.TransactionMutation;
import org.springframework.data.repository.CrudRepository;

public interface TransactionMutationRepository extends CrudRepository<TransactionMutation, Integer> {
    Iterable<TransactionMutation> findAllByTransactionId(Integer transactionId);

    default Iterable<TransactionMutation> findAllByTransactionId(TransactionMutation transactionMutation) {
        return findAllByTransactionId(transactionMutation == null ? null : transactionMutation.getTransaction().getId());
    }
}
