package blackjack.model.player;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.model.bet.Bet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntryTest {

    @DisplayName("이미 배팅한 상태에서 또 배팅하면 예외가 발생한다")
    @Test
    void bet_exception_already_betted() {
        Entry entry = Entry.from("entry");
        entry.bet(Bet.from(10000));

        assertThatThrownBy(() -> entry.bet(Bet.from(20000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 배팅하였습니다.");
    }
}
