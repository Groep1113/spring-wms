package seeder;

import com.google.common.collect.Lists;
import entity.Location;
import entity.Supplier;
import entity.Item;
import repository.LocationRepository;
import repository.SupplierRepository;
import repository.ItemRepository;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ItemLocationSeeder extends Seeder {
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

    ItemLocationSeeder(LocationRepository locationRepository, ItemRepository itemRepository) {
        super(locationRepository, "item_locations");
        this.locationRepository = locationRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    void seedJob() {
        Iterable<Location> locationIterable = locationRepository.findAll();
        Iterable<Item> items = itemRepository.findAll();
        ArrayList<Location> locations = Lists.newArrayList(locationIterable);
        Random rand = new Random();
        for(Item i: items) {
            Location location = locations.get(rand.nextInt(locations.size()));
            i.getLocations().add(location);
            itemRepository.save(i);
        }
    }
}
