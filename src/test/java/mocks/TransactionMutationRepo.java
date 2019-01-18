package mocks;

import entity.TransactionMutation;
import repository.TransactionMutationRepository;

import java.util.Optional;

public class TransactionMutationRepo implements TransactionMutationRepository {
    @Override
    public Iterable<TransactionMutation> findAllByTransactionId(Integer transactionId) {
        return null;
    }

    @Override
    public <S extends TransactionMutation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TransactionMutation> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TransactionMutation> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<TransactionMutation> findAll() {
        return null;
    }

    @Override
    public Iterable<TransactionMutation> findAllById(Iterable<Integer> integers) {
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
    public void delete(TransactionMutation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TransactionMutation> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
