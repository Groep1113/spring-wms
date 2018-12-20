package repository;

import entity.Account;
import entity.Balance;
import entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {

    Optional<Balance> findByAccountAndItem(Account account, Item item);

    default Optional<Balance> findByAccountAndItem(Balance balance) {
        return findByAccountAndItem(balance == null ? null : balance.getAccount(), balance == null ? null : balance.getItem());
    }

    Optional<Balance> findByItem(Item item);

    default Optional<Balance> findByItem(Balance balance) {
        return findByItem(balance == null ? null : balance.getItem());
    }
}
