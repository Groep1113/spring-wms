package seeder;

import entity.Category;
import org.springframework.data.repository.CrudRepository;

public class CategorySeeder extends TableSeeder{

    public static final String[] categories = {"Kabels", "Laptops", "Toetsenborden", "Printers", "Scanners", "Overig"};
    public CategorySeeder(CrudRepository repository) {
        super(repository, "category");
    }

    @Override
    void seedJob() {
        for(String categoryName: categories) {
            Category c = new Category();
            c.setName(categoryName);
            repository.save(c);
        }
    }
}
