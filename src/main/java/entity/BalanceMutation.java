package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class BalanceMutation {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Item item;

    @NotNull
    private Integer amount;

    private String reason;

    public BalanceMutation(Account account, Item item, @NotNull Integer amount, String reason) {
        this.account = account;
        this.item = item;
        this.amount = amount;
        this.reason = reason;
    }

    public BalanceMutation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
