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

    public Item(String name, String code, Integer recommendedStock, Location location) {
        this.name = name;
        this.code = code;
        this.recommendedStock = recommendedStock;
        this.locations = new HashSet<>();
        locations.add(location);
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
}
