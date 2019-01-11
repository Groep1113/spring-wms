package mocks;

import entity.Supplier;
import repository.SupplierRepository;

import java.util.Optional;

public class SupplierRepo implements SupplierRepository {
    @Override
    public Optional<Supplier> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public <S extends Supplier> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Supplier> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Supplier> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Supplier> findAll() {
        return null;
    }

    @Override
    public Iterable<Supplier> findAllById(Iterable<Integer> integers) {
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
    public void delete(Supplier entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Supplier> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
