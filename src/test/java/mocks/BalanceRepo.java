package mocks;

import entity.Account;
import entity.Balance;
import entity.Item;
import repository.BalanceRepository;

import java.util.Optional;

public class BalanceRepo implements BalanceRepository {
    @Override
    public Optional<Balance> findByAccountAndItem(Account account, Item item) {
        return Optional.empty();
    }

    @Override
    public Optional<Balance> findByItem(Item item) {
        return Optional.empty();
    }

    @Override
    public <S extends Balance> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Balance> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Balance> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Balance> findAll() {
        return null;
    }

    @Override
    public Iterable<Balance> findAllById(Iterable<Integer> integers) {
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
    public void delete(Balance entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Balance> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
