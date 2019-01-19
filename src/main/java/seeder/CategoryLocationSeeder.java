package seeder;

import com.google.common.collect.Lists;
import entity.Category;
import entity.Location;
import repository.CategoryRepository;
import repository.LocationRepository;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class CategoryLocationSeeder extends Seeder {
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    CategoryLocationSeeder(CategoryRepository categoryRepository, LocationRepository locationRepository) {
        super(categoryRepository, "category_locations");
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    void seedJob() {
        Iterable<Category> categories = categoryRepository.findAll();
        Iterable<Location> locationIterable = locationRepository.findAll();
        ArrayList<Location> locations = Lists.newArrayList(locationIterable);
        Random rand = new Random();
        for(Category c: categories) {
            Set<Location> categoryLocations = c.getLocations();
            Location location = locations.get(rand.nextInt(locations.size()));
            categoryLocations.add(location);
            categoryRepository.save(c);
        }
    }
}
