package blackjack.view;

import blackjack.model.bet.Profit;
import blackjack.model.player.Dealer;
import blackjack.model.player.Entry;
import blackjack.model.player.Hand;
import blackjack.model.player.Name;
import blackjack.model.player.Player;
import blackjack.model.trumpcard.TrumpCard;
import blackjack.model.trumpcard.TrumpDenomination;
import blackjack.model.trumpcard.TrumpSuit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultView {
    private static final String FORMAT_MESSAGE_HAND_INITIALIZED = "%n%s와 %s에게 2장의 카드를 나누었습니다.%n";
    private static final String FORMAT_HAND_INITIALIZED = "%s : %s%n";
    private static final String FORMAT_MESSAGE_BUST = "%n%s의 점수 합이 21을 넘어, 다음 참가자로 넘어갑니다.%n%n";
    private static final String FORMAT_MESSAGE_DEALER_HIT = "%n%s는 16이하라 %d장의 카드를 더 받았습니다.%n";
    private static final String FORMAT_SCORE = "%s 카드: %s - 결과: %d%n";
    private static final String TITLE_PROFIT = "\n## 최종 수익";
    private static final String FORMAT_PROFIT = "%s : %d%n";

    private static final String DELIMITER_JOIN = ", ";
    private static final Map<TrumpDenomination, String> DENOMINATION_NAMES = TrumpCardNames.getDenominationNames();
    private static final Map<TrumpSuit, String> SUIT_NAMES = TrumpCardNames.getSuitNames();

    public void printFirstHands(Dealer dealer, List<Entry> entries) {
        printThatHandInitialized(dealer.getName(), collectEntryNamesFrom(entries));
        printOnlyFirstCard(dealer.getName(), dealer.getHand());
        for (Entry entry : entries) {
            printFullHand(entry);
        }
    }

    private List<Name> collectEntryNamesFrom(List<Entry> entries) {
        return entries.stream()
                .map(Entry::getName)
                .collect(Collectors.toList());
    }

    private void printOnlyFirstCard(Name name, Hand hand) {
        System.out.printf(FORMAT_HAND_INITIALIZED,
                name.getValue(), cardToName(hand.getCards().get(0)));
    }

    public void printFullHand(Entry entry) {
        printFullHand(entry.getName(), entry.getHand());
    }

    private void printFullHand(Name name, Hand hand) {
        System.out.printf(FORMAT_HAND_INITIALIZED,
                name.getValue(), joinHand(hand));
    }

    private void printThatHandInitialized(Name dealerName, List<Name> entryNames) {
        System.out.printf(
                FORMAT_MESSAGE_HAND_INITIALIZED,
                dealerName.getValue(), joinNames(entryNames));
    }

    private String joinNames(List<Name> names) {
        List<String> nameValues = names.stream()
                .map(Name::getValue)
                .collect(Collectors.toList());
        return String.join(DELIMITER_JOIN, nameValues);
    }

    public void printBustMessage(Name name) {
        System.out.printf(FORMAT_MESSAGE_BUST, name.getValue());
    }

    public void printDealerAddedCount(Dealer dealer) {
        System.out.printf(FORMAT_MESSAGE_DEALER_HIT,
                dealer.getName().getValue(), dealer.getHand().countAddedCards());
    }

    public void printResults(Map<Player, Profit> profits) {
        printScores(profits.keySet());
        printProfits(profits);
    }

    private void printScores(Set<Player> players) {
        for (Player player : players) {
            printScore(player);
        }
    }

    private void printScore(Player player) {
        System.out.printf(FORMAT_SCORE, player.getName().getValue(),
                joinHand(player.getHand()), player.getScore());
    }

    private String joinHand(Hand hand) {
        return joinStrings(hand.getCards().stream()
                .map(this::cardToName)
                .collect(Collectors.toList()));
    }

    private String cardToName(TrumpCard card) {
        return trumpDenominationToString(card.getDenomination()) + trumpSuitToString(card.getSuit());
    }

    private String trumpDenominationToString(TrumpDenomination trumpDenomination) {
        if (DENOMINATION_NAMES.get(trumpDenomination) == null) {
            return String.valueOf(trumpDenomination.getValue());
        }
        return DENOMINATION_NAMES.get(trumpDenomination);
    }

    private String trumpSuitToString(TrumpSuit trumpSuit) {
        return SUIT_NAMES.get(trumpSuit);
    }

    private void printProfits(Map<Player, Profit> profits) {
        System.out.println(TITLE_PROFIT);
        for (Player player : profits.keySet()) {
            printProfit(player.getName(), profits.get(player));
        }
    }

    private void printProfit(Name name, Profit profit) {
        System.out.printf(FORMAT_PROFIT, name.getValue(), profit.getValue());
    }

    private String joinStrings(List<String> strings) {
        return String.join(DELIMITER_JOIN, strings);
    }
}
