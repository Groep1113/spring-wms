package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Balance {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer amount;

    public Balance(@NotNull Integer amount) {
        this.amount = amount;
    }

    public Balance() {
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
}
