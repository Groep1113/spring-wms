package mocks;

import entity.Account;
import entity.Location;
import repository.AccountRepository;

import java.util.Optional;

public class AccountRepo implements AccountRepository {
    @Override
    public Optional<Account> findByName(String name) {
        return Optional.of(new Account(name));
    }

    @Override
    public Optional<Account> findByLocationId(int locationId) {
        Account account = new Account("account", new Location("A1", 1, 1,1));
        return Optional.of(account);
    }

    @Override
    public <S extends Account> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Account> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Account> findAll() {
        return null;
    }

    @Override
    public Iterable<Account> findAllById(Iterable<Integer> integers) {
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
    public void delete(Account entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
