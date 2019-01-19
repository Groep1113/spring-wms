package seeder;

import entity.Item;
import entity.Transaction;
import entity.TransactionLine;
import org.springframework.data.repository.CrudRepository;
import repository.ItemRepository;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionLineSeeder extends Seeder {
    private final ItemRepository itemRepository;
    private final TransactionRepository transactionRepository;

    TransactionLineSeeder(CrudRepository repository, TransactionRepository transactionRepository, ItemRepository itemRepository) {
        super(repository, "transaction_line");
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    void seedJob() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        Random rand = new Random();
        for(Transaction transaction: transactionRepository.findAll()) {
            for (int i = 0; i < 2; i++) {
                TransactionLine tl = new TransactionLine();
                tl.setAmount(rand.nextInt(10) + 1);
                tl.setItem(items.get(rand.nextInt(items.size())));
                tl.setTransaction(transaction);
                repository.save(tl);
            }
        }
    }
}
