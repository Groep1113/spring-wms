package repository;

import entity.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    Optional<Supplier> findByName(String name);

    default Optional<Supplier> findByName(Supplier supplier) {
        return findByName(supplier == null ? null : supplier.getName());
    }

}
