package seeder;

import entity.Category;
import entity.Item;
import entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import repository.CategoryRepository;
import repository.SupplierRepository;

import java.util.Optional;
import java.util.Set;

public class ItemSeeder extends Seeder {

    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    ItemSeeder(CrudRepository repository,
               CategoryRepository categoryRepository,
               SupplierRepository supplierRepository) {
        super(repository, "item");
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    void seedJob() {
        String[] prefixes = {"A", "B", "C", "E", "F", "H", "X"};
        String[] postfixes = {"1", "3", "5", "6", "7", "8", "9"};
        String[] types = CategorySeeder.categories;

        Optional<Supplier> supplier = supplierRepository.findByName("Default Test Supplier");

        for(int i = 0; i < 10; i++) {
            String prefix = DatabaseSeeder.getNFromStringArray(3, prefixes);
            String postfix = DatabaseSeeder.getNFromStringArray(5, postfixes);
            Item item = new Item();
            item.setCode(prefix + postfix);
            String type = DatabaseSeeder.getNFromStringArray(1, types);
            item.setName(type + DatabaseSeeder.getNFromStringArray(3, postfixes));
            item.setRecommendedStock(10);
            if(supplier.isPresent()) item.setSupplier(supplier.get());

            repository.save(item);
            Set<Category> itemCategories = item.getCategories();
            for(Category c: categoryRepository.findAll()) {
                if(c.getName().equals(type)) {
                    itemCategories.add(c);
                    break;
                }
            }
            repository.save(item);
        }
    }
}
