package entity;


import graphql.GraphQLException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Account from;

    @ManyToOne
    private Account to;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate updateDate;

    private LocalDate deletedDate;

    private LocalDate receivedDate;

    @NotNull
    private Boolean locked;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER)
    private Set<TransactionRule> transactionRules;

    public Transaction(Account from, Account to) {
        this.from = from;
        this.to = to;
        this.createdDate = LocalDate.now();
        this.updateDate = LocalDate.now();
        this.locked = false;
    }

    public Transaction(Account from, Account to, @NotNull LocalDate createdDate, @NotNull LocalDate updateDate, @NotNull Boolean locked) {
        this.from = from;
        this.to = to;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.locked = locked;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.updateDate = LocalDate.now();
        this.createdDate = createdDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.updateDate = updateDate;
    }

    public LocalDate getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.deletedDate = deletedDate;
        this.updateDate = LocalDate.now();
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.receivedDate = receivedDate;
        this.updateDate = LocalDate.now();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        if (this.locked) throw new GraphQLException("Transaction is locked and therefore, no changes can be made.");
        this.to = to;
    }

    public Set<TransactionRule> getTransactionRules() {
        return transactionRules;
    }

    public void setTransactionRules(Set<TransactionRule> transactionRules) {
        this.transactionRules = transactionRules;
    }
}
