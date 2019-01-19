package seeder;

import entity.Category;
import entity.Location;
import org.springframework.data.repository.CrudRepository;
import repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LocationSeeder extends TableSeeder{

    LocationSeeder(CrudRepository repository) {
        super(repository, "location");
    }

    @Override
    void seedJob() {
        String[] locationCharacter = {"A", "B", "C", "D", "E", "F"};
        String[] locationNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for(int i = 0; i < 10; i++) {
            Location location = new Location();
            location.setDepth(50);
            location.setHeight(50);
            location.setWidth(50);
            location.setCode(DatabaseSeeder.getNFromStringArray(1, locationCharacter) + DatabaseSeeder.getNFromStringArray(1, locationNumbers));
            repository.save(location);
        }
    }
}
