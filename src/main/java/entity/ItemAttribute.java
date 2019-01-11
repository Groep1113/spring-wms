package entity;

import javax.persistence.*;

@Entity
public class ItemAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    public ItemAttribute() {
    }

    public ItemAttribute(String name, String value, Item item) {
        this.name = name;
        this.value = value;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
