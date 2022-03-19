package blackjack.model.bet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResultTest {

    @DisplayName("15000원을 배팅해서 Blackjack이 나오면 배팅금의 1.5배인 22500원을 얻는다")
    @Test
    void apply_blackjack_10000() {
        Profit profit = Result.BLACKJACK.apply(Bet.from(15000));

        assertThat(profit.getValue()).isEqualTo(22500);
    }

    @DisplayName("20000원을 배팅해서 패배하면 배팅금 20000원을 잃는다")
    @Test
    void apply_lose_20000() {
        Profit profit = Result.LOSE.apply(Bet.from(20000));

        assertThat(profit.getValue()).isEqualTo(-20000);
    }

    @DisplayName("30000원을 배팅해서 승리하면 배팅금 30000원을 얻는다")
    @Test
    void apply_win_30000() {
        Profit profit = Result.WIN.apply(Bet.from(30000));

        assertThat(profit.getValue()).isEqualTo(30000);
    }

    @DisplayName("10000원을 배팅해서 무승부이면 수익도 손해도 없다")
    @Test
    void apply_tie_10000() {
        Profit profit = Result.TIE.apply(Bet.from(10000));

        assertThat(profit.getValue()).isEqualTo(0);
    }
}
