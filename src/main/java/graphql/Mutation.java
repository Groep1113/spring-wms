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

    public Item createItem(String name, String code, int recommended_stock) throws GraphQLException {
        return itemRepository.save(new Item(name, code, recommended_stock));
    }

    public Location createLocation(String code, int depth, int width, int height) throws GraphQLException {
        return locationRepository.save(new Location(code, depth, width, height));
    }

}
