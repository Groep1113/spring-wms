package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

    public static final String WAREHOUSE = "Warehouse";
    public static final String IN_USE = "In use";

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public Account(@NotNull String name) {
        this.name = name;
    }

    public Account() {
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
}
