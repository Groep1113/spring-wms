package mocks;

import entity.TransactionLine;
import repository.TransactionLineRepository;

import java.util.Optional;

public class TransactionLineRepo implements TransactionLineRepository {
    @Override
    public Iterable<TransactionLine> findAllByTransactionId(Integer transactionId) {
        return null;
    }

    @Override
    public Iterable<TransactionLine> findAllByItemId(Integer itemId) {
        return null;
    }

    @Override
    public <S extends TransactionLine> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TransactionLine> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TransactionLine> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<TransactionLine> findAll() {
        return null;
    }

    @Override
    public Iterable<TransactionLine> findAllById(Iterable<Integer> integers) {
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
    public void delete(TransactionLine entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TransactionLine> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
