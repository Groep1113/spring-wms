package repository;

import entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    //create
    <S extends Location> S save(S entity);

    //readAll
    Iterable<Location> findAll();

    //delete
    void delete(Location entity);
}
