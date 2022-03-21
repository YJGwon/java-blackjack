package blackjack.model.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.model.bet.Bet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntriesTest {
    private List<Name> names;
    private List<Bet> bets;

    @BeforeEach
    void initializeNamesAndBets() {
        names = Name.from(List.of("포키", "리버"));
        bets = Bet.from(List.of(1000, 1000));
    }

    @DisplayName("포키와 리버가 있을 때, 리버의 턴을 시작한다")
    @Test
    void nextEntry_reaver() {
        Entries entries = Entries.from(names, bets);

        final int testingIndex = 1;
        for (int i = 0; i <= testingIndex; i++) {
            entries.toNextEntry();
        }

        assertThat(entries.getCurrentEntryName()).isEqualTo(new Name("리버"));
    }

    @DisplayName("더 이상 Entry가 없을 때 예외가 발생한다.")
    @Test
    void nextEntry_exception_no_entry() {
        Entries entries = Entries.from(names, bets);
        for (int i = 0; i < names.size(); i++) {
            entries.toNextEntry();
        }

        assertThatThrownBy(entries::toNextEntry)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[ERROR] 더 이상 Entry가 없습니다.");
    }
}
