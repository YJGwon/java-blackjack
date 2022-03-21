package blackjack.model.player;

import blackjack.model.trumpcard.TrumpCard;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Hand {
    private static final String ERROR_ALREADY_INITIALIZED = "[ERROR] 이미 덱을 초기화했습니다.";

    private static final int FIRST_DECK_SIZE = 2;
    private static final int SCORE_LIMIT = 21;
    private static final int SCORE_ACE_ADVANTAGE = 10;
    private static final int SCORE_ADVANTAGE_CRITERIA = SCORE_LIMIT - SCORE_ACE_ADVANTAGE;

    private final List<TrumpCard> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    void initialize(Supplier<TrumpCard> cardSupplier) {
        checkInitialized();
        for (int i = 0; i < FIRST_DECK_SIZE; i++) {
            add(cardSupplier.get());
        }
    }

    private void checkInitialized() {
        if (this.cards.size() != 0) {
            throw new IllegalArgumentException(ERROR_ALREADY_INITIALIZED);
        }
    }

    void add(TrumpCard card) {
        this.cards.add(card);
    }

    boolean isBust() {
        return sumScore() > SCORE_LIMIT;
    }

    boolean isBlackjack() {
        return !hasCardAdded() && sumScore() == SCORE_LIMIT;
    }

    boolean hasCardAdded() {
        return countAddedCards() > 0;
    }

    int countAddedCards() {
        return getSize() - FIRST_DECK_SIZE;
    }

    int sumScore() {
        int score = 0;
        score = sumCardDenominationTo(score);
        if (hasAce()) {
            return sumAceAdvantageTo(score);
        }
        return score;
    }

    private int sumCardDenominationTo(int score) {
        for (TrumpCard card : cards) {
            score = card.sumDenominationTo(score);
        }
        return score;
    }

    private boolean hasAce() {
        return cards.stream()
                .anyMatch(TrumpCard::isAce);
    }

    private int sumAceAdvantageTo(int score) {
        int aceCount = countAce();
        for (int i = 0; i < aceCount; i++) {
            score += choiceAceAdvantage(score);
        }
        return score;
    }

    private int countAce() {
        return (int) cards.stream()
                .filter(TrumpCard::isAce)
                .count();
    }

    private int choiceAceAdvantage(int score) {
        if (score <= SCORE_ADVANTAGE_CRITERIA) {
            return SCORE_ACE_ADVANTAGE;
        }
        return 0;
    }

    boolean isScoreLessThan(Hand hand) {
        return this.isScoreLessThan(hand.sumScore());
    }

    boolean isScoreLessThan(int otherScore) {
        return sumScore() < otherScore;
    }

    public List<TrumpCard> getCards() {
        return new ArrayList<>(cards);
    }

    int getSize() {
        return this.cards.size();
    }
}
