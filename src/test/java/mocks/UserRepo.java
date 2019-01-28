package mocks;

import entity.User;
import repository.UserRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepo implements UserRepository {
    private HashMap<Integer, User> usersWithId = new HashMap<>();

    public UserRepo() {
        User user = new User();
        user.setFirstName("unit");
        user.setLastName("test");
        user.setEmail("unitTest@bs-htg.nl");
        user.setPassword("habbo123");
        user.setRoles(new HashSet<>());
        user.setId(1);
        usersWithId.put(1, user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email.equals("unitTest@bs-htg.nl")) {
            return Optional.of(usersWithId.get(1));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        if (email.equals("unitTest@bs-htg.nl") && password.equals("habbo123")) {
            User user = new User();
            user.setEmail("unitTest@bs-htg.nl");
            user.setPassword("habbo123");
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public <S extends User> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        if (!usersWithId.containsKey(id)) return Optional.empty();
        return Optional.of(usersWithId.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return usersWithId.values().parallelStream().collect(Collectors.toList());
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> integers) {
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
    public void delete(User entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
