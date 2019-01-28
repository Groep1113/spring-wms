package repository;

import entity.Item;
import entity.Suggestion;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SuggestionRepository extends CrudRepository<Suggestion, Integer> {

    Optional<Suggestion> findByItem(Item item);

}
