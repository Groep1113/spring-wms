package graphql;

import base.TokenManager;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import entity.Item;
import entity.Location;
import entity.User;
import graphql.schema.DataFetchingEnvironment;
import graphql.types.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ItemRepository;
import repository.LocationRepository;
import repository.UserRepository;

import java.util.HashSet;
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

    public LoginPayload login(String email, String password) throws GraphQLException {
        User user = userRepository
            .authenticate(email, password)
            .orElse(null);
        if (user == null) throw new GraphQLException("Invalid login");

        String token = TokenManager.generateToken();
        user.setToken(token);
        userRepository.save(user);

        return new LoginPayload(token, user);
    }

    public Item createItem(String name, String code, int recommended_stock, int locationId) throws GraphQLException {
        return itemRepository.save(new Item(name, code, recommended_stock, locationRepository.findById(locationId).get()));
    }

    public Location createLocation(String code, int depth, int width, int height) throws GraphQLException {
        return locationRepository.save(new Location(code, depth, width, height));
    }

    // TODO: pas de repository aan zodat deze code wat netter wordt.
    // TODO: pas de repository aan zodat de setters ook many-to-many zijn
    public Item itemChangeLocation(int id, int locationId) throws GraphQLException {
        Location location = locationRepository.findById(locationId).get();
        Set<Location> locations = new HashSet<>();
        locations.add(location);
        Item item = itemRepository.findById(id).get();
        item.setLocations(locations);
        itemRepository.save(item);
        return item;
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteItem(int id) throws GraphQLException {
        if (itemRepository.existsById(id)) {
            itemRepository.delete(itemRepository.findById(id).get());
            return true;
        }
        else { return false; }
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteLocation(int id) throws GraphQLException {
        if (locationRepository.existsById(id)) {
            locationRepository.delete(locationRepository.findById(id).get());
            return true;
        }
        else { return false; }
    }
}
