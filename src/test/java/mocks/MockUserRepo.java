package mocks;

import entity.User;
import repository.UserRepository;

import java.util.Optional;

public class MockUserRepo implements UserRepository {
    @Override
    public Optional<User> findByEmail(String email) {
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
        return Optional.empty();
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
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
