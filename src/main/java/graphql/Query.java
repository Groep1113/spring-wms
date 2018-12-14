package graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import entity.Category;
import entity.Item;
import entity.Location;
import entity.User;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.CategoryRepository;
import repository.ItemRepository;
import repository.LocationRepository;
import repository.UserRepository;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public Query(UserRepository userRepository,
                 ItemRepository itemRepository,
                 LocationRepository locationRepository,
                 CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }



    // These method names have to line up with the schema.graphqls field definitions
    // (these are the get methods for our graphql schema)

    public List<User> getUsers(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<User>) userRepository.findAll());
    }

    public User getUserByMail(String email, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return userRepository
            .findByEmail(email)
            .orElse(null);
    }

    public User getUser(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return userRepository
            .findById(id)
            .orElse(null);
    }

    public List<Item> getItems(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Item>) itemRepository.findAll());
    }

    public Item getItem(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return itemRepository.findById(id).orElse(null);
    }

    public List<Location> getLocations(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Location>) locationRepository.findAll());
    }

    public Location getLocation(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return locationRepository.findById(id).orElse(null);
    }

    public Category getCategory(Integer id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getCategories(DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return ((List<Category>) categoryRepository.findAll());
    }

}