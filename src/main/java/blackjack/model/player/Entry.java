package blackjack.model.player;

import blackjack.model.bet.Bet;
import blackjack.model.bet.Profit;
import blackjack.model.bet.Result;

public final class Entry extends Player {
    private static final String ERROR_ALREADY_BETTED = "[ERROR] 이미 배팅하였습니다.";

    private Bet bet;

    private Entry(Name name) {
        super(name);
    }

    public static Entry from(String rawName) {
        return new Entry(new Name(rawName));
    }

    void bet(Bet bet) {
        if (this.bet != null) {
            throw new IllegalArgumentException(ERROR_ALREADY_BETTED);
        }
        this.bet = bet;
    }

    Profit winProfit(Result result) {
        return result.apply(this.bet);
    }
}
