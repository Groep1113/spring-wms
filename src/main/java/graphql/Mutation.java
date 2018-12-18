package graphql;

import base.TokenManager;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import entity.Category;
import entity.Item;
import entity.Location;
import entity.User;
import graphql.types.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.CategoryRepository;
import repository.ItemRepository;
import repository.LocationRepository;
import repository.UserRepository;
<<<<<<< HEAD

import java.util.HashSet;
import java.util.Optional;
=======
>>>>>>> 5e6c48fd673c9a4b89e25beb298174d0a4893fcc
import java.util.Set;

@Component
public class Mutation implements GraphQLMutationResolver {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public Mutation(UserRepository userRepository,
                    ItemRepository itemRepository,
                    LocationRepository locationRepository,
                    CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    private String idNotFoundMessage(int id, String entity){
        return "No " + entity.toLowerCase() + " found with id " + id + ".";
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

<<<<<<< HEAD
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
=======
    public Item createItem(String name, String code, int recommendedStock, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return itemRepository.save(new Item(
                        name,
                        code,
                        recommendedStock,
                        locationRepository
                            .findById(locationId)
                            .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())))));
    }

    public Location createLocation(String code, int depth, int width, int height, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);
        
        return locationRepository.save(new Location(code, depth, width, height));
    }

    public Category createCategory(String name, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        return categoryRepository.save(new Category(name));
    }

    public Item itemChangeLocation(int itemId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));
        Set<Location> locations = item.getLocations();

        Location location = locationRepository
                .findById(locationId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())));

        locations.add(location);
        return itemRepository.save(item);
    }

    public Item itemAddCategory(int itemId, int categoryId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Set<Category> categories = item.getCategories();
        for (Category cat: categories) {
            if (cat.getId() == categoryId) { throw new GraphQLException("Item (id:" + itemId + ") is already added to this category (id:" + categoryId + ")."); }
        }
        categories.add(category);
        return itemRepository.save(item);
    }

    public Item itemRemoveCategory(int itemId, int categoryId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(itemId, Item.class.getSimpleName())));

        Set<Category> categories = item.getCategories();
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                categories.remove(category);
                return itemRepository.save(item);
            }
        }
        throw new GraphQLException("This item (id:" + itemId + ") is not part of this category (id:" + categoryId + "). Therefore, it can not be removed from it.");
    }

    public Category categoryChangeName(int id, String name, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Category.class.getSimpleName())));

        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category categoryAddLocation(int categoryId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Location location = locationRepository
                .findById(locationId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(locationId, Location.class.getSimpleName())));

        Set<Location> locations = category.getLocations();
        for (Location loc: locations) {
            if (loc.getId() == locationId) { throw new GraphQLException("Location (id:" + locationId+ ") is already added to this category (id:" + categoryId + ")."); }
        }
        locations.add(location);
        return categoryRepository.save(category);
    }

    public Category categoryRemoveLocation(int categoryId, int locationId, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(categoryId, Category.class.getSimpleName())));

        Set<Location> locations = category.getLocations();
        for (Location location : locations) {
            if (location.getId() == locationId) {
                locations.remove(location);
                return categoryRepository.save(category);
            }
        }
        throw new GraphQLException("This location (id:" + locationId + ") is not part of this category (id:" + categoryId + "). Therefore, it can not be removed from it.");
    }

    public Boolean deleteItem(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        itemRepository.delete(itemRepository
                .findById(id)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Item.class.getSimpleName()))));
        return true;
    }

    public Boolean deleteLocation(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        locationRepository.delete(locationRepository
                .findById(id)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Location.class.getSimpleName()))));
        return true;
    }

    public Boolean deleteCategory(int id, DataFetchingEnvironment env) {
        AuthContext.requireAuth(env);

        categoryRepository.delete(categoryRepository
                .findById(id)
                .orElseThrow(() -> new GraphQLException(idNotFoundMessage(id, Category.class.getSimpleName()))));
        return true;
>>>>>>> 5e6c48fd673c9a4b89e25beb298174d0a4893fcc
    }
}
