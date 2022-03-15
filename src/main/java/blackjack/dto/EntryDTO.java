package blackjack.dto;

import blackjack.model.player.Entry;
import blackjack.model.Game;
import java.util.List;
import java.util.stream.Collectors;

public final class EntryDTO extends PlayerDTO {
    private EntryDTO(String name, DeckDTO deck, int score) {
        super(name, deck, score);
    }

    public static List<EntryDTO> from(Game game) {
        return game.getEntries().stream()
                .map(EntryDTO::from)
                .collect(Collectors.toList());
    }

    public static EntryDTO fromCurrent(Game game) {
        return from(game.getCurrentEntry());
    }

    public static EntryDTO from(Entry entry) {
        return new EntryDTO(entry.getName(), DeckDTO.from(entry), entry.getScore());
    }
}
