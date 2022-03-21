package blackjack.model.player;

import blackjack.model.bet.Bet;
import blackjack.model.bet.Profits;
import blackjack.model.bet.Result;
import blackjack.model.trumpcard.TrumpCard;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Entries {
    private static final String ERROR_NO_ENTRY = "[ERROR] 더 이상 Entry가 없습니다.";
    private static final int INITIAL_INDEX = -1;

    private final List<Entry> values;
    private int currentIndex = INITIAL_INDEX;

    private Entries(List<Entry> values) {
        this.values = List.copyOf(values);
    }

    static Entries from(List<Name> names, List<Bet> bets) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            entries.add(new Entry(names.get(i), bets.get(i)));
        }
        return new Entries(entries);
    }

    void initializeHands(Supplier<TrumpCard> cardSupplier) {
        for (Entry entry : values) {
            entry.initializeHand(cardSupplier);
        }
    }

    void toNextEntry() {
        if (hasNoNext()) {
            throw new RuntimeException(ERROR_NO_ENTRY);
        }
        this.currentIndex++;
    }

    boolean hasNoNext() {
        return values.size() <= currentIndex + 1;
    }

    void addToCurrentEntry(TrumpCard card) {
        getCurrentEntry().addCard(card);
    }

    boolean isCurrentEntryBust() {
        return getCurrentEntry().isBust();
    }

    Profits compareAllWith(Dealer dealer) {
        return Profits.of(this.values.stream()
                .collect(Collectors.toMap(
                        entry -> entry,
                        entry -> entry.winProfit(compare(dealer, entry)),
                        (a, b) -> b,
                        LinkedHashMap::new)), dealer);
    }

    private Result compare(Dealer dealer, Entry entry) {
        return dealer.compareWith(entry);
    }

    Entry getCurrentEntry() {
        return this.values.get(currentIndex);
    }

    Name getCurrentEntryName() {
        return getCurrentEntry().getName();
    }

    List<Entry> getValues() {
        return this.values;
    }
}
