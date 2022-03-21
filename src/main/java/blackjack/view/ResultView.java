package blackjack.view;

import blackjack.dto.DealerDto;
import blackjack.dto.HandDto;
import blackjack.dto.PlayerDto;
import blackjack.dto.PlayersDto;
import blackjack.dto.ProfitsDto;
import blackjack.dto.TrumpCardDto;
import blackjack.model.player.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public void printFirstHands(Name dealerName, List<Name> entryNames, PlayersDto players) {
        printThatHandInitialized(dealerName, entryNames);
        printOnlyFirstCard(dealerName, players.getDealer());
        for (int i = 0; i < entryNames.size(); i++) {
            printFullHand(entryNames.get(i), players.getEntries().get(i));
        }
    }

    private void printOnlyFirstCard(Name name, DealerDto dealer) {
        System.out.printf(FORMAT_HAND_INITIALIZED,
                name.getValue(), concatFirstCardToString(dealer.getHand()));
    }

    private String concatFirstCardToString(HandDto hand) {
        TrumpCardDto firstCard = hand.getCards().get(0);
        return firstCard.getDenomination() + firstCard.getSuit();
    }

    public void printFullHand(Name name, PlayerDto player) {
        System.out.printf(FORMAT_HAND_INITIALIZED,
                name.getValue(), joinHand(player.getHand()));
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

    public void printDealerAddedCount(Name name, DealerDto dealer) {
        System.out.printf(FORMAT_MESSAGE_DEALER_HIT,
                name.getValue(), dealer.getAddedCount());
    }

    public void printResults(Name dealerName, List<Name> entryNames,
                             PlayersDto players, ProfitsDto profits) {
        List<Name> allNames = new ArrayList<>();
        allNames.add(dealerName);
        allNames.addAll(entryNames);
        printScores(allNames, players);
        printProfits(dealerName, entryNames, profits);
    }

    private void printScores(List<Name> names, PlayersDto players) {
        for (int i = 0; i < names.size(); i++) {
            printScore(names.get(i), players.getPlayers().get(i));
        }
    }

    private void printScore(Name name, PlayerDto player) {
        System.out.printf(FORMAT_SCORE, name.getValue(),
                joinHand(player.getHand()), player.getScore());
    }

    private String joinHand(HandDto hand) {
        return joinStrings(hand.getCards().stream()
                .map(card -> card.getDenomination() + card.getSuit())
                .collect(Collectors.toList()));
    }

    private void printProfits(Name dealerName, List<Name> entryNames, ProfitsDto profits) {
        System.out.println(TITLE_PROFIT);
        printProfit(dealerName, profits.getDealerProfit());
        printEntryProfits(entryNames, profits);
    }

    private void printEntryProfits(List<Name> names, ProfitsDto profits) {
        Map<Name, Integer> entryProfits = profits.getEntryProfits();
        for (Name entryName : names) {
            printProfit(entryName, entryProfits.get(entryName));
        }
    }

    private void printProfit(Name name, int profit) {
        System.out.printf(FORMAT_PROFIT, name.getValue(), profit);
    }

    private String joinStrings(List<String> strings) {
        return String.join(DELIMITER_JOIN, strings);
    }
}
