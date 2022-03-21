package blackjack;

import blackjack.model.Game;
import blackjack.model.bet.Bet;
import blackjack.model.bet.Profits;
import blackjack.model.player.Name;
import blackjack.view.InputView;
import blackjack.view.ResultView;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();

        List<Name> names = Name.from(inputView.askEntryNames());
        List<Bet> bets = Bet.from(betMoney(names, inputView));
        Game game = new Game(names, bets);
        giveFirstHands(game, resultView);
        playEntries(game, inputView, resultView);
        inputView.closeInput();
        hitDealer(game, resultView);
        showResult(game, resultView);
    }

    private static List<Integer> betMoney(List<Name> names, InputView inputView) {
        return names.stream()
                .map(inputView::askBetAmount)
                .collect(Collectors.toList());
    }

    private static void giveFirstHands(Game game, ResultView resultView) {
        game.giveFirstHands();
        resultView.printFirstHands(game.getDealer(), game.getEntries());
    }

    private static void playEntries(Game game, InputView inputView, ResultView resultView) {
        do {
            game.toNextEntry();
            playTurn(game, inputView, resultView);
        } while (game.hasNextEntry());
    }

    private static void playTurn(Game game, InputView inputView, ResultView resultView) {
        if (!game.canCurrentEntryHit()) {
            resultView.printBustMessage(game.getCurrentEntryName());
            return;
        }
        hitCurrentEntry(game, inputView, resultView);
    }

    private static void hitCurrentEntry(Game game, InputView inputView, ResultView resultView) {
        if (inputView.askForHit(game.getCurrentEntryName())) {
            game.hitCurrentEntry();
            resultView.printFullHand(game.getCurrentEntry());
            playTurn(game, inputView, resultView);
        }
    }

    private static void hitDealer(Game game, ResultView resultView) {
        if (game.hitDealer()) {
            resultView.printDealerAddedCount(game.getDealer());
        }
    }

    private static void showResult(Game game, ResultView resultView) {
        Profits profits = game.calculateProfits();
        resultView.printResults(profits.getValues());
    }
}
