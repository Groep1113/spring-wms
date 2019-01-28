package controller;

import entity.*;
import org.springframework.stereotype.Component;
import repository.BalanceRepository;
import repository.ItemRepository;
import repository.SuggestionRepository;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Checks for given transactions whether or not suggestions should be created.
 */
@Component
public class SuggestionController {

    private final TransactionRepository transactionRepository;
    private final SuggestionRepository suggestionRepository;
    private final BalanceRepository balanceRepository;
    private final ItemRepository itemRepository;
    private static SuggestionController instance = null;
    private final String REASON_BELOW_RECOMMENDED = "Replenish %s to avoid dropping below recommended stock.";
    private final String REASON_BELOW_ZERO = "Replenish %s to avoid running out of stock.";

    public SuggestionController(TransactionRepository transactionRepository,
                                SuggestionRepository suggestionRepository,
                                BalanceRepository balanceRepository,
                                ItemRepository itemRepository) {
        this.transactionRepository = transactionRepository;
        this.suggestionRepository = suggestionRepository;
        this.balanceRepository = balanceRepository;
        this.itemRepository = itemRepository;
        SuggestionController.setInstance(this);
    }

    private static void setInstance(SuggestionController s) {
        SuggestionController.instance = s;
    }

    public static SuggestionController getInstance() {
        return SuggestionController.instance;
    }
    /**
     * Given a transaction object, determines whether the inventory for its items are plentiful enough.
     * @param transaction The transaction which lines need checking
     */
    public void checkForPotentialSuggestions(Transaction transaction) {
        if (!transaction.getFromAccount().getName().equals(Account.WAREHOUSE))
            return;

        for (TransactionLine transactionLine : transaction.getTransactionLines()){
            Item item = transactionLine.getItem();
            Optional<Balance> itemBalanceOptional = balanceRepository.findByAccountAndItem(transaction.getFromAccount(), item);
            if(!itemBalanceOptional.isPresent())
                continue;
            Balance itemBalance = itemBalanceOptional.get();
            Integer plannedDepletionAmount = 0;
            for(Transaction plannedTransaction: this.transactionRepository.findByPlannedDateGreaterThanContainingItemNotReceived(LocalDate.now(), item)) {
                if(!plannedTransaction.getFromAccount().getName().equals(Account.WAREHOUSE))
                    continue;
                for(TransactionLine plannedTransactionLine: plannedTransaction.getTransactionLines()) {
                    if(plannedTransactionLine.getItem().getId().equals(item.getId())) {
                        plannedDepletionAmount += plannedTransactionLine.getAmount();
                    }
                }
            }
            Integer plannedBalance = itemBalance.getAmount() - plannedDepletionAmount;
            if(plannedBalance <= 0) {
                createOrUpdateSuggestion(item, REASON_BELOW_ZERO, item.getRecommendedStock() + Math.abs(plannedBalance));
            } else if(plannedBalance <= item.getRecommendedStock()) {
                createOrUpdateSuggestion(item, REASON_BELOW_RECOMMENDED, item.getRecommendedStock() - plannedBalance);
            }
        }

    }

    private void createOrUpdateSuggestion(Item item, String reason, Integer amount) {
        Optional<Suggestion> existingSuggestion = this.suggestionRepository.findByItem(item);
        Suggestion suggestion;
        if(existingSuggestion.isPresent()) {
            suggestion = existingSuggestion.get();
        } else {
            suggestion = new Suggestion();
        }
        suggestion.setItem(item);
        suggestion.setReason(reason.replaceFirst("(%s)", item.getName()));
        suggestion.setAmount(amount);
        this.suggestionRepository.save(suggestion);
    }
}
