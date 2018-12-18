package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String code;
    private Integer recommendedStock;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private Set<Location> locations = new HashSet<>();

<<<<<<< HEAD
=======
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private Set<Category> categories;

>>>>>>> 5e6c48fd673c9a4b89e25beb298174d0a4893fcc
    public Item(String name, String code, Integer recommendedStock, Location location) {
        this.name = name;
        this.code = code;
        this.recommendedStock = recommendedStock;
        this.locations = new HashSet<>();
        locations.add(location);
        this.categories = new HashSet<>();
    }

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

<<<<<<< HEAD
    public Integer setRecommendedStock() {
=======
    public Integer getRecommendedStock() {
>>>>>>> 5e6c48fd673c9a4b89e25beb298174d0a4893fcc
        return recommendedStock;
    }

    public void setRecommendedStock(Integer recommendedStock) {
        this.recommendedStock = recommendedStock;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
