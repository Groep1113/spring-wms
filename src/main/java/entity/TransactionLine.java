package entity;

import graphql.GraphQLException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TransactionLine {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer amount;

    @ManyToOne
    private Transaction transaction;

    @OneToOne
    private Item item;

    public TransactionLine(@NotNull Integer amount, Transaction transaction, Item item) {
        if (transaction.getLocked()) throw new GraphQLException(Transaction.LOCKED_MESSAGE);
        this.amount = amount;
        this.transaction = transaction;
        this.item = item;
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
