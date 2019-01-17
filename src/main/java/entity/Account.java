package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Account {

    public static final String WAREHOUSE = "Warehouse";
    public static final String IN_USE = "In use";
    public static final String SUPPLIER = "Supplier";
    public static final String WRITE_OFF = "Write off";
    public static final String MANUAL = "Manual";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Location location;

    private LocalDate deletedDate;

    public Account(@NotNull String name) {
        this.name = name;
    }

    public Account(String name, Location location) {
        this.name = name;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }
}
