package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate updateDate;

    private LocalDate deletedDate;

    private LocalDate receivedDate;

    @NotNull
    private Boolean locked;

    public Transaction(@NotNull LocalDate createdDate, @NotNull LocalDate updateDate, @NotNull Boolean locked) {
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
        this.createdDate = createdDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDate getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
