package seeder;

import entity.Item;
import entity.Suggestion;
import entity.Transaction;
import entity.TransactionLine;
import repository.ItemRepository;
import repository.SuggestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuggestionSeeder extends Seeder {
    private final ItemRepository itemRepository;
    private final SuggestionRepository suggestionRepository;


    public SuggestionSeeder(SuggestionRepository suggestionRepository, ItemRepository itemRepository) {
        super(suggestionRepository, "suggestion");
        this.itemRepository = itemRepository;
        this.suggestionRepository = suggestionRepository;
    }


    @Override
    void seedJob() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        Random rand = new Random();
        for(int i = 0; i < 3; i++) {

            Suggestion s = new Suggestion();
            s.setItem(items.get(
                    rand.nextInt(items.size()))
            );
            s.setReason("It's always nice to have more!");
            s.setAmount(5);

        }
    }
}
