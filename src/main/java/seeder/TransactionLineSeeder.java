package seeder;

import entity.*;
import org.springframework.data.repository.CrudRepository;
import repository.ItemRepository;
import repository.LocationRepository;
import repository.TransactionRepository;

import java.util.*;

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
            Account toAccount = transaction.getToAccount();
            Item usedItem = null;
            for (int i = 0; i < 2; i++) {
                if(usedItem == null) {
                    usedItem = findItemWithLocation(items, toAccount.getLocation());
                } else {
                    usedItem = findItemWithLocationExcluding(items, toAccount.getLocation(), usedItem);
                }
                if(usedItem == null)
                    continue;
                TransactionLine tl = new TransactionLine();
                tl.setAmount(rand.nextInt(10) + 1);
                tl.setItem(usedItem);
                tl.setTransaction(transaction);
                repository.save(tl);
            }
        }
    }

    private Item findItemWithLocation(List<Item> items, Location location) {
        return findItemWithLocationExcluding(items, location, null);
    }

    private Item findItemWithLocationExcluding(List<Item> items, Location location, Item exclusion) {
        Item foundItem = null;
        Iterator<Item> itemIterator = items.iterator();
        while(foundItem == null && itemIterator.hasNext()) {
            Item currentItem = itemIterator.next();
            if(currentItem.equals(exclusion))
                continue;
            for(Location itemLocation: currentItem.getLocations()) {
                if(itemLocation.getId().equals(location.getId())) {
                    foundItem = currentItem;
                    break;
                }
            }
        }
        return foundItem;
    }
}
