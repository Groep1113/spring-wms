package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class TransactionLine {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer amount;

    private LocalDate plannedDate;

    @ManyToOne
    private Transaction transaction;

    @OneToOne
    private Item item;

    public TransactionLine(@NotNull Integer amount, Transaction transaction, Item item, LocalDate plannedDate) {
        this.amount = amount;
        this.transaction = transaction;
        this.item = item;
        this.plannedDate = plannedDate;
    }

    public TransactionLine() {
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

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
