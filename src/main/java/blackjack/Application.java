package blackjack;

import blackjack.model.Game;
import blackjack.model.bet.Profits;
import blackjack.view.InputView;
import blackjack.view.ResultView;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();

        Game game = new Game(inputView.askEntryNames());
        betMoney(game, inputView);
        giveFirstHands(game, resultView);
        playEntries(game, inputView, resultView);
        inputView.closeInput();
        hitDealer(game, resultView);
        showResult(game, resultView);
    }

    private static void betMoney(Game game, InputView inputView) {
        do {
            game.toNextEntry();
            game.betToCurrentEntry(inputView.askBetAmount(game.getCurrentEntryName()));
        } while (game.hasNextEntry());
        game.toFirstEntry();
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
