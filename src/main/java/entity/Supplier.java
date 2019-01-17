package entity;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Supplier {
    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Item> items;

    // getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void removeItem(Item i) {
        Optional<Item> result = this.items.stream()
            .parallel()
            .filter(item -> item.getId().equals(i.getId()))
            .findAny();
        result.ifPresent(item -> this.items.remove(item));
    }
}