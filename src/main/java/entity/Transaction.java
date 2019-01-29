package entity;

import graphql.GraphQLException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate updateDate;

    private LocalDate deletedDate;

    private LocalDate plannedDate;

    private LocalDate receivedDate;

    private String description;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER)
    private Set<TransactionMutation> transactionMutations;

    @NotNull
    private Boolean locked;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER)
    private Set<TransactionLine> transactionLines;

    @Transient
    static final String LOCKED_MESSAGE = "Transaction is locked and therefore, no changes can be made.";

    public Transaction(Account fromAccount, Account toAccount, LocalDate plannedDate, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.createdDate = LocalDate.now();
        this.updateDate = LocalDate.now();
        this.plannedDate = plannedDate == null ? LocalDate.now() : plannedDate;
        this.description = description;
        this.transactionLines = new HashSet<>();
        this.locked = false;
    }

    public Transaction() {
        this.transactionLines = new HashSet<>();
        this.locked = false;
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
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.updateDate = LocalDate.now();
        this.createdDate = createdDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.updateDate = updateDate;
    }

    public LocalDate getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.deletedDate = deletedDate;
        this.updateDate = LocalDate.now();
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.plannedDate = plannedDate;
        this.updateDate = LocalDate.now();
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.receivedDate = receivedDate;
        this.updateDate = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.description = description;
        this.updateDate = LocalDate.now();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.locked = locked;
        this.updateDate = LocalDate.now();
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.updateDate = LocalDate.now();
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.updateDate = LocalDate.now();
        this.toAccount = toAccount;
    }

    public Set<TransactionLine> getTransactionLines() {
        return transactionLines;
    }

    public void setTransactionLines(Set<TransactionLine> transactionLines) {
        if (this.locked) throw new GraphQLException(LOCKED_MESSAGE);
        this.transactionLines = transactionLines;
    }

    public Set<TransactionMutation> getTransactionMutations() {
        return transactionMutations;
    }

    public void setTransactionMutations(Set<TransactionMutation> transactionMutations) {
        this.transactionMutations = transactionMutations;
    }
}
