package blackjack.view;

import blackjack.dto.EntryDto;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String QUESTION_NAME = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";
    private static final String FORMAT_BETTING_MONEY = "%n%s의 배팅 금액은?%n";
    private static final String FORMAT_HIT = "%n%s는 한장의 카드를 더 받겠습니까?(예는 %s, 아니오는 %s)%n";
    private static final String ERROR_NUMBER_FORMAT = "[ERROR] 정수로만 입력해주세요.";
    private static final String ERROR_NO_SUCH_SIGN = "[ERROR] y또는 n으로만 입력하세요.";

    private static final String REGEX_NAME = ",";
    private static final String SIGN_TRUE = "y";
    private static final String SIGN_FALSE = "n";

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public List<String> askEntryNames() {
        System.out.println(QUESTION_NAME);
        String namesInput = scanner.nextLine();
        return splitNames(namesInput);
    }

    private List<String> splitNames(String rawNames) {
        return Arrays.asList((rawNames.split(REGEX_NAME)));
    }

    public int askBetAmount(EntryDto entry) {
        System.out.printf(FORMAT_BETTING_MONEY, entry.getName());
        return readInt();
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_NUMBER_FORMAT);
        }
    }

    public boolean askForHit(EntryDto entry) {
        System.out.printf(FORMAT_HIT, entry.getName(), SIGN_TRUE, SIGN_FALSE);
        String input = scanner.nextLine();
        return isInputTrue(input.trim());
    }

    private boolean isInputTrue(String input) {
        if (input.equals(SIGN_TRUE)) {
            return true;
        }
        if (input.equals(SIGN_FALSE)) {
            return false;
        }
        throw new IllegalArgumentException(ERROR_NO_SUCH_SIGN);
    }

    public void closeInput() {
        scanner.close();
    }
}
