package blackjack.view;

import blackjack.model.trumpcard.TrumpDenomination;
import blackjack.model.trumpcard.TrumpSuit;
import java.util.EnumMap;
import java.util.Map;

public final class TrumpCardNames {
    private static final Map<TrumpDenomination, String> DENOMINATION_NAMES;
    private static final String NAME_ACE = "A";
    private static final String NAME_JACK = "J";
    private static final String NAME_QUEEN = "Q";
    private static final String NAME_KING = "K";

    private static final Map<TrumpSuit, String> SUIT_NAMES;
    private static final String NAME_HEART = "하트";
    private static final String NAME_DIAMOND = "다이아몬드";
    private static final String NAME_CLOVER = "클로버";
    private static final String NAME_SPADE = "스페이드";

    static {
        DENOMINATION_NAMES = new EnumMap<>(TrumpDenomination.class);
        DENOMINATION_NAMES.put(TrumpDenomination.ACE, NAME_ACE);
        DENOMINATION_NAMES.put(TrumpDenomination.JACK, NAME_JACK);
        DENOMINATION_NAMES.put(TrumpDenomination.QUEEN, NAME_QUEEN);
        DENOMINATION_NAMES.put(TrumpDenomination.KING, NAME_KING);

        SUIT_NAMES = new EnumMap<>(TrumpSuit.class);
        SUIT_NAMES.put(TrumpSuit.HEART, NAME_HEART);
        SUIT_NAMES.put(TrumpSuit.DIAMOND, NAME_DIAMOND);
        SUIT_NAMES.put(TrumpSuit.CLOVER, NAME_CLOVER);
        SUIT_NAMES.put(TrumpSuit.SPADE, NAME_SPADE);
    }

    public static Map<TrumpDenomination, String> getDenominationNames() {
        return Map.copyOf(DENOMINATION_NAMES);
    }

    public static Map<TrumpSuit, String> getSuitNames() {
        return Map.copyOf(SUIT_NAMES);
    }
}
