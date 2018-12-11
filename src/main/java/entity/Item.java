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
    private Integer recommended_stock;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private Set<Location> locations = new HashSet<Location>();

    public Item(String name, String code, Integer recommended_stock, Location location) {
        this.name = name;
        this.code = code;
        this.recommended_stock = recommended_stock;
        this.locations = new HashSet<>();
        locations.add(location);
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

    public Integer getRecommended_stock() {
        return recommended_stock;
    }

    public void setRecommended_stock(Integer recommended_stock) {
        this.recommended_stock = recommended_stock;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
}
