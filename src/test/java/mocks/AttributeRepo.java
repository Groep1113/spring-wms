package mocks;

import entity.Attribute;
import repository.AttributeRepository;

import java.util.Optional;

public class AttributeRepo implements AttributeRepository {
    @Override
    public <S extends Attribute> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Attribute> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Attribute> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Attribute> findAll() {
        return null;
    }

    @Override
    public Iterable<Attribute> findAllById(Iterable<Integer> integers) {
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
    public void delete(Attribute entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Attribute> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
