package blackjack.model.player;

import blackjack.model.trumpcard.Hand;
import blackjack.model.trumpcard.TrumpCard;
import java.util.function.Supplier;

public abstract class Player {
    private final Name name;
    private final Hand hand;

    protected Player(Name name) {
        this.name = name;
        this.hand = new Hand();
    }

    protected void initializeHand(Supplier<TrumpCard> cardSupplier) {
        this.hand.initialize(cardSupplier);
    }

    protected void addCard(TrumpCard card) {
        this.hand.add(card);
    }

    protected boolean canHit(int criteria) {
        return this.hand.isScoreLessThan(criteria);
    }

    protected int countAddedCards() {
        return this.hand.countAddedCards();
    }

    protected boolean isBust() {
        return this.hand.isBust();
    }

    protected boolean isBlackjack() {
        return this.hand.isBlackjack();
    }

    protected boolean isScoreLessThen(Player player) {
        return this.hand.isScoreLessThan(player.hand);
    }

    public int getScore() {
        return hand.sumScore();
    }

    public Name getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
    }
}
