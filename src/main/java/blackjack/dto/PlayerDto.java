package blackjack.dto;

public abstract class PlayerDto {
    private final HandDto hand;
    private final int score;

    protected PlayerDto(HandDto hand, int score) {
        this.hand = hand;
        this.score = score;
    }

    public HandDto getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }
}
