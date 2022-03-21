package blackjack.model.player;

import blackjack.model.bet.Bet;
import blackjack.model.bet.Profit;
import blackjack.model.bet.Result;

public final class Entry extends Player {
    private final Bet bet;

    Entry(Name name, Bet bet) {
        super(name);
        this.bet = bet;
    }

    Profit winProfit(Result result) {
        return result.apply(this.bet);
    }
}
