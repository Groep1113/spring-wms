package repository;

import entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByName(String name);

    default Optional<Account> findByName(Account account) {
        return findByName(account == null ? null : account.getName());
    }


}
