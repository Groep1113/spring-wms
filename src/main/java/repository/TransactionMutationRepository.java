package repository;

import entity.TransactionMutation;
import org.springframework.data.repository.CrudRepository;

public interface TransactionMutationRepository extends CrudRepository<TransactionMutation, Integer> {
}
