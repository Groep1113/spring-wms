package mocks;

import entity.TransactionRule;
import repository.TransactionRepository;
import repository.TransactionRuleRepository;

import java.util.Optional;

public class TransactionRuleRepo implements TransactionRuleRepository {
    @Override
    public Iterable<TransactionRule> findAllByTransactionId(Integer transactionId) {
        return null;
    }

    @Override
    public Iterable<TransactionRule> findAllByItemId(Integer itemId) {
        return null;
    }

    @Override
    public <S extends TransactionRule> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TransactionRule> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TransactionRule> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<TransactionRule> findAll() {
        return null;
    }

    @Override
    public Iterable<TransactionRule> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(TransactionRule entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TransactionRule> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
