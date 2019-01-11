package mocks;

import entity.Location;
import repository.LocationRepository;

import java.util.Optional;

public class LocationRepo implements LocationRepository {
    @Override
    public <S extends Location> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Location> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Location> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Location> findAll() {
        return null;
    }

    @Override
    public Iterable<Location> findAllById(Iterable<Integer> integers) {
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
    public void delete(Location entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Location> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
