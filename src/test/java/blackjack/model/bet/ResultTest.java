package blackjack.model.bet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResultTest {
    private Bet bet;

    @BeforeEach()
    void initializeBet() {
        bet = Bet.from(List.of(15000)).get(0);
    }

    @DisplayName("15000원을 배팅해서 Blackjack이 나오면 배팅금의 1.5배인 22500원을 얻는다")
    @Test
    void apply_blackjack_10000() {
        Profit profit = Result.BLACKJACK.apply(bet);

        assertThat(profit.getValue()).isEqualTo(22500);
    }

    @DisplayName("15000원을 배팅해서 패배하면 배팅금 15000원을 잃는다")
    @Test
    void apply_lose_15000() {
        Profit profit = Result.LOSE.apply(bet);

        assertThat(profit.getValue()).isEqualTo(-15000);
    }

    @DisplayName("15000원을 배팅해서 승리하면 배팅금 15000원을 얻는다")
    @Test
    void apply_win_30000() {
        Profit profit = Result.WIN.apply(bet);

        assertThat(profit.getValue()).isEqualTo(15000);
    }

    @DisplayName("15000원을 배팅해서 무승부이면 수익도 손해도 없다")
    @Test
    void apply_tie_10000() {
        Profit profit = Result.TIE.apply(bet);

        assertThat(profit.getValue()).isEqualTo(0);
    }
}
