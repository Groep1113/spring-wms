package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class BalanceMutation {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer amount;

    private String reason;

    public BalanceMutation(@NotNull Integer amount) {
        this.amount = amount;
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
}
