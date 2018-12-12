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

    public Item createItem(String name, String code, int recommended_stock, int locationId, DataFetchingEnvironment env) throws GraphQLException {
        AuthContext.requireAuth(env);

        return itemRepository
                .save(new Item(
                        name,
                        code,
                        recommended_stock,
                        locationRepository
                            .findById(locationId)
                            .orElseThrow(() -> new GraphQLException("No location found with id " + locationId + "."))));
    }

    public Location createLocation(String code, int depth, int width, int height, DataFetchingEnvironment env) throws GraphQLException {
        AuthContext.requireAuth(env);
        
        return locationRepository.save(new Location(code, depth, width, height));
    }

    // TODO: pas de repository aan zodat deze code wat netter wordt.
    // TODO: pas de repository aan zodat de setters ook many-to-many zijn
    public Item itemChangeLocation(int itemId, int locationId, DataFetchingEnvironment env) throws GraphQLException {
        AuthContext.requireAuth(env);

        Location location = locationRepository.findById(locationId).orElseThrow(() -> new GraphQLException("No location found with id " + locationId + "."));
        Set<Location> locations = new HashSet<>();
        locations.add(location);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new GraphQLException("No item found with id " + itemId + "."));
        item.setLocations(locations);
        itemRepository.save(item);
        return item;
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteItem(int id, DataFetchingEnvironment env) throws GraphQLException {
        AuthContext.requireAuth(env);

        itemRepository.delete(itemRepository.findById(id).orElseThrow(() -> new GraphQLException("No item found with id " + id + ".")));
        return true;
    }

    // TODO: pas repository aan zodat boolean terug wordt gegeven.
    public Boolean deleteLocation(int id, DataFetchingEnvironment env) throws GraphQLException {
        AuthContext.requireAuth(env);

        locationRepository.delete(locationRepository.findById(id).orElseThrow(() -> new GraphQLException("No location found with id " + id + ".")));
        return true;
    }
}
