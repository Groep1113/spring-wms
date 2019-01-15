package entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionMutation  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime date;

    private String description;

    public TransactionMutation() {
    }

    public TransactionMutation(Transaction transaction, User user, LocalDateTime date, String description) {
        this.transaction = transaction;
        this.user = user;
        this.date = date;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
