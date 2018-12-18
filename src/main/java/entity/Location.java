package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String code;
    private Integer depth;
    private Integer width;
    private Integer height;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "locations")
    private Set<Item> items = new HashSet<>();
<<<<<<< HEAD
=======

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "locations")
    private Set<Category> categories = new HashSet<>();
>>>>>>> 5e6c48fd673c9a4b89e25beb298174d0a4893fcc

    public Location(String code, Integer depth, Integer width, Integer height) {
        this.code = code;
        this.depth = depth;
        this.width = width;
        this.height = height;
    }

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
