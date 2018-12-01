package repository;

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

}