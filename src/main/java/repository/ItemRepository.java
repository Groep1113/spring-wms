package repository;

import entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {


    //create
    <S extends Item> S save(S entity);

    //readAll
    Iterable<Item> findAll();

    //delete
    void delete(Item entity);
}
