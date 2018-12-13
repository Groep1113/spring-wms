package graphql;

import base.TokenManager;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import entity.Item;
import entity.Location;
import entity.User;
import graphql.types.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ItemRepository;
import repository.LocationRepository;
import repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class Mutation implements GraphQLMutationResolver {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;

    @Autowired
    public Mutation(UserRepository userRepository,
                    ItemRepository itemRepository,
                    LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    public LoginPayload login(String email, String password) {
        User user = userRepository
            .authenticate(email, password)
            .orElse(null);
        if (user == null) throw new GraphQLException("Invalid login");

        String token = TokenManager.generateToken();
        user.setToken(token);
        userRepository.save(user);

        return new LoginPayload(token, user);
    }

    public Item createItem(String name, String code, int recommendedStock, int locationId) {
        Optional<Location> optLocation = locationRepository.findById(locationId);
        if (!optLocation.isPresent()) throw new GraphQLException("Could not find locationId");

        return itemRepository.save(
            new Item(name, code, recommendedStock, optLocation.get())
        );
    }

    public Location createLocation(String code, int depth, int width, int height) {
        return locationRepository.save(new Location(code, depth, width, height));
    }

    // TODO: pas de repository aan zodat deze code wat netter wordt.
    // TODO: pas de repository aan zodat de setters ook many-to-many zijn
    public Item itemChangeLocation(int id, int locationId) {
        Optional<Location> optLocation = locationRepository.findById(locationId);
        Optional<Item> optItem = itemRepository.findById(id);
        if (optItem.isPresent() && optLocation.isPresent()) {
            Item item = optItem.get();
            Set<Location> locations = new HashSet<>(1);
            locations.add(optLocation.get());

            item.setLocations(locations);
            return itemRepository.save(item);
        }
        throw new GraphQLException("Invalid location or item id");
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteItem(int id) {
        Optional<Item> optItem = itemRepository.findById(id);
        if (optItem.isPresent()) {
            itemRepository.delete(optItem.get());
            return true;
        }
        throw new GraphQLException("Item does not exist");
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteLocation(int id) {
        Optional<Location> optLocation = locationRepository.findById(id);
        if (optLocation.isPresent()) {
            locationRepository.delete(optLocation.get());
            return true;
        }
        throw new GraphQLException("Location does not exist");
    }
}
