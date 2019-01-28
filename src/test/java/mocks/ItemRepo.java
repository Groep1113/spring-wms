package mocks;

import entity.Item;
import entity.Location;
import repository.ItemRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ItemRepo implements ItemRepository {
    @Override
    public <S extends Item> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Item> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Item> findById(Integer integer) {
        Set<Location> locationSet = new HashSet<>();
        locationSet.add(new Location("A1", 1,1,1));
        return Optional.of(new Item("Lenovo Yoga", "YOGA", 5, locationSet, null, null));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Item> findAll() {
        return null;
    }

    @Override
    public Iterable<Item> findAllById(Iterable<Integer> integers) {
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
    public void delete(Item entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Item> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
