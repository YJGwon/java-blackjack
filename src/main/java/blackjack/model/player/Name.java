package blackjack.model.player;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Name {
    private static final String ERROR_NULL = "[ERROR] 입력된 이름이 없습니다.";
    private static final String ERROR_BLANK = "[ERROR] 이름은 공백일 수 없습니다.";
    private static final String ERROR_MAX_LENGTH = "[ERROR] 이름은 15자 이하로 입력해주세요.";
    private static final String ERROR_CONTAINS_NUMBER = "[ERROR] 이름에 숫자는 포함될 수 없습니다.";
    private static final String ERROR_CONTAINS_SIGN = "[ERROR] 이름에 기호는 포함될 수 없습니다.";
    private static final String ERROR_DUPLICATE = "[ERROR] 중복된 이름이 있습니다.";

    private static final String REGEX_NAME_CONTAINS_NUMBER = "^\\D*[0-9]+\\D*$";
    private static final Pattern PATTERN_NAME_CONTAINS_NUMBER = Pattern.compile(REGEX_NAME_CONTAINS_NUMBER);
    private static final String REGEX_NAME_CONTAINS_SIGN = "^\\D*[!\"#$%&'()*+,./:;<=>?@\\\\^_`{|}~-]+\\D*$";
    private static final Pattern PATTERN_NAME_CONTAINS_SIGN = Pattern.compile(REGEX_NAME_CONTAINS_SIGN);

    private static final int MAX_LENGTH = 15;

    private final String value;

    Name(String value) {
        validate(value);
        this.value = value.trim();
    }

    public static List<Name> from(List<String> rawNames) {
        checkDuplicate(rawNames);
        return rawNames.stream()
                .map(Name::new)
                .collect(Collectors.toList());
    }

    private static void checkDuplicate(List<String> rawNames) {
        if (countDistinct(rawNames) != rawNames.size()) {
            throw new IllegalArgumentException(ERROR_DUPLICATE);
        }
    }

    private static int countDistinct(List<String> rawNames) {
        return (int) rawNames.stream()
                .map(String::trim)
                .distinct()
                .count();
    }

    private void validate(String value) {
        checkNull(value);
        checkBlank(value);
        checkLength(value);
        checkNumberIn(value);
        checkSignIn(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException(ERROR_NULL);
        }
    }

    private void checkBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(ERROR_BLANK);
        }
    }

    private void checkLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MAX_LENGTH);
        }
    }

    private void checkNumberIn(String value) {
        if (PATTERN_NAME_CONTAINS_NUMBER.matcher(value).matches()) {
            throw new IllegalArgumentException(ERROR_CONTAINS_NUMBER);
        }
    }

    private void checkSignIn(String value) {
        if (PATTERN_NAME_CONTAINS_SIGN.matcher(value).matches()) {
            throw new IllegalArgumentException(ERROR_CONTAINS_SIGN);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
