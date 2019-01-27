package entity;

import javax.persistence.*;

@Entity
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer amount;

    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    public Suggestion() {
    }

    public Suggestion(Integer amount, String reason, Item item) {
        this.amount = amount;
        this.reason = reason;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
