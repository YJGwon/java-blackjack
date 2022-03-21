package blackjack.model.player;

import blackjack.model.bet.Bet;
import blackjack.model.bet.Profits;
import blackjack.model.trumpcard.TrumpCard;
import java.util.List;
import java.util.function.Supplier;

public final class Players {
    private final Entries entries;
    private final Dealer dealer;

    private Players(Entries entries, Dealer dealer) {
        this.entries = entries;
        this.dealer = dealer;
    }

    public static Players from(List<Name> names, List<Bet> bets) {
        return new Players(Entries.from(names, bets), new Dealer());
    }

    public void initializeHands(Supplier<TrumpCard> cardSupplier) {
        this.dealer.initializeHand(cardSupplier);
        this.entries.initializeHands(cardSupplier);
    }

    public boolean hasNextEntry() {
        return !this.entries.hasNoNext();
    }

    public void toNextEntry() {
        this.entries.toNextEntry();
    }

    public void addToCurrentEntry(TrumpCard card) {
        this.entries.addToCurrentEntry(card);
    }

    public boolean isCurrentEntryBust() {
        return this.entries.isCurrentEntryBust();
    }

    public void hitDealer(TrumpCard card) {
        this.dealer.hit(card);
    }

    public boolean canDealerHit() {
        return this.dealer.canHit();
    }

    public Profits calculateProfits() {
        return this.entries.compareAllWith(dealer);
    }

    public Entry getCurrentEntry() {
        return this.entries.getCurrentEntry();
    }

    public Name getCurrentEntryName() {
        return this.entries.getCurrentEntry().getName();
    }

    public List<Entry> getEntries() {
        return this.entries.getValues();
    }

    public Dealer getDealer() {
        return this.dealer;
    }
}
