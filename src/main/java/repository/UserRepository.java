package repository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.repository.CrudRepository;

import entity.User;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    default Optional<User> findByEmail(User user) {
        return findByEmail(user == null ? null : user.getEmail());
    }

    Optional<User> findByToken(String token);

    default Optional<User> findByToken(User user) {
        return findByToken(user == null ? null : user.getToken());
    }

    // @TODO: maybe return the token String instead
    default Optional<User> authenticate(String email, String password) {
        Optional optUser = this.findByEmail(email);
        if (!optUser.isPresent()) return Optional.empty();
        User user = ((User) optUser.get());
        if (!BCrypt.checkpw(password, user.getPassword())) return Optional.empty();
        return Optional.of(user);
    }
}