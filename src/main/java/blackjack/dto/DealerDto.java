package blackjack.dto;

import blackjack.model.Game;
import blackjack.model.player.Dealer;

public final class DealerDto extends PlayerDto {
    private final int addedCount;

    private DealerDto(HandDto hand, int addedCount, int score) {
        super(hand, score);
        this.addedCount = addedCount;
    }

    public static DealerDto from(Game game) {
        return from(game.getDealer(), game.countCardsAddedToDealer());
    }

    private static DealerDto from(Dealer dealer, int addedCount) {
        return new DealerDto(
                HandDto.from(dealer), addedCount, dealer.getScore());
    }

    public int getAddedCount() {
        return addedCount;
    }
}
