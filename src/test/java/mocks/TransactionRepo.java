package mocks;

import entity.Account;
import entity.Transaction;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class TransactionRepo implements TransactionRepository {

    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Iterable<Transaction> findAllByFromAccountNameAndToAccountName(String fromName, String toName) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    @Override
    public <S extends Transaction> S save(S entity) {
        Transaction transaction = entity;
        transaction.setId(1);
        return entity;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(Integer integer) {
        Account account = new Account("account");
        Transaction transaction = new Transaction(account, account, null, "description");
        transaction.setId(integer);
        transaction.setTransactionLines(new HashSet<>());
        return Optional.of(transaction);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Transaction> findAll() {
        return null;
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Transaction entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
