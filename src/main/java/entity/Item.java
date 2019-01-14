package entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private Set<Attribute> attributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Supplier supplier;

    public Item(String name, String code, Integer recommendedStock, Set<Location> locations, Category category, Supplier supplier) {
        this.name = name;
        if (code != null) this.code = code;
        if (recommendedStock != null) this.recommendedStock = recommendedStock;
        if (locations != null) this.locations = locations;
        if (category != null) categories.add(category);
        if (supplier != null) this.supplier = supplier;
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

    public Integer getRecommendedStock() {
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
