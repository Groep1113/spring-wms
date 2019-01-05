package repository;

import entity.TransactionRule;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRuleRepository extends CrudRepository<TransactionRule, Integer> {

    Iterable<TransactionRule> findAllByTransactionId(Integer transactionId);

    default Iterable<TransactionRule> findAllByTransactionId(TransactionRule transactionRule) {
        return findAllByTransactionId(transactionRule == null ? null : transactionRule.getTransaction().getId());
    }

    Iterable<TransactionRule> findAllByItemId(Integer itemId);

    default Iterable<TransactionRule> findAllByItemId(TransactionRule transactionRule) {
        return findAllByItemId(transactionRule == null ? null : transactionRule.getItem().getId());
    }


}
