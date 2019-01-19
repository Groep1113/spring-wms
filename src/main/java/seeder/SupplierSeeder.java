package seeder;

import entity.Supplier;
import org.springframework.data.repository.CrudRepository;

public class SupplierSeeder extends TableSeeder{
    SupplierSeeder(CrudRepository repository) {
        super(repository, "supplier");
    }

    @Override
    void seedJob() {
        Supplier supplier = new Supplier();
        supplier.setName("Default Test Supplier");
        repository.save(supplier);
    }
}
