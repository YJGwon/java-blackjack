package blackjack.model;

import blackjack.model.bet.Bet;
import blackjack.model.bet.Profits;
import blackjack.model.player.Dealer;
import blackjack.model.player.Entry;
import blackjack.model.player.Players;
import blackjack.model.trumpcard.TrumpCard;
import blackjack.model.trumpcard.TrumpCardPack;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Game {
    private final Players players;
    private final TrumpCardPack trumpCardPack;

    public Game(List<String> names) {
        this.trumpCardPack = new TrumpCardPack();
        this.players = Players.from(names);
    }

    public void betToCurrentEntry(Function<Game, Integer> betInputFunction) {
        int amount = betInputFunction.apply(this);
        this.players.betToCurrent(Bet.from(amount));
    }

    public void start() {
        this.players.initializeHands(supplyCard());
    }

    private Supplier<TrumpCard> supplyCard() {
        return trumpCardPack::draw;
    }

    public boolean hasNextEntry() {
        return this.players.hasNextEntry();
    }

    public void toNextEntry() {
        this.players.toNextEntry();
    }

    public void resetEntriesCursor() {
        this.players.resetEntriesCursor();
    }

    public void hitCurrentEntry() {
        this.players.addToCurrentEntry(trumpCardPack.draw());
    }

    public boolean canCurrentEntryHit() {
        return this.players.isCurrentEntryBust();
    }

    public void hitDealer() {
        while (this.players.canDealerHit()) {
            this.players.hitDealer(trumpCardPack.draw());
        }
    }

    public int countCardsAddedToDealer() {
        return this.players.countCardsAddedToDealer();
    }

    public Profits getProfits() {
        return this.players.getProfits();
    }

    public List<Entry> getEntries() {
        return this.players.getEntries();
    }

    public Dealer getDealer() {
        return this.players.getDealer();
    }

    public Entry getCurrentEntry() {
        return this.players.getCurrentEntry();
    }
}
