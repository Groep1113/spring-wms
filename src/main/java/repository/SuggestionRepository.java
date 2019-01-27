package repository;

import entity.Suggestion;
import org.springframework.data.repository.CrudRepository;

public interface SuggestionRepository extends CrudRepository<Suggestion, Integer> {
}
