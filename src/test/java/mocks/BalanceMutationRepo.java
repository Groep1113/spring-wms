package mocks;

import entity.BalanceMutation;
import repository.BalanceMutationRepository;

import java.util.Optional;

public class BalanceMutationRepo implements BalanceMutationRepository {
    @Override
    public <S extends BalanceMutation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends BalanceMutation> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<BalanceMutation> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<BalanceMutation> findAll() {
        return null;
    }

    @Override
    public Iterable<BalanceMutation> findAllById(Iterable<Integer> integers) {
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
    public void delete(BalanceMutation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends BalanceMutation> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
