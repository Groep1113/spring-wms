package mocks;

import entity.Account;
import entity.Location;
import repository.LocationRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class LocationRepo implements LocationRepository {
    private HashMap<Integer, Location> locationStore = new HashMap<>();

    public LocationRepo() {
        Location loc = new Location();
//        loc.setAccount();
        loc.setCode("UT1");
        loc.setDepth(0);
        loc.setWidth(0);
        loc.setHeight(0);
        loc.setId(1);
        locationStore.put(1, loc);
    }

    @Override
    public <S extends Location> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Location> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Location> findById(Integer id) {
        if (locationStore.containsKey(id)) return Optional.of(locationStore.get(id));
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
