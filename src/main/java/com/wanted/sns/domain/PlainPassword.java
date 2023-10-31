package com.wanted.sns.domain;

import com.wanted.sns.exception.*;
import jakarta.persistence.*;
import java.util.*;
import java.util.regex.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PlainPassword {

    private static final int PASSWORD_MINIMUM_LENGTH = 10;
    private static final String PASSWORD_FORMAT_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{10,}$";
    private static final Pattern PASSWORD_FORMAT_PATTERN = Pattern.compile(PASSWORD_FORMAT_REGEX);
    private static final int MAX_SAME_CONTINUOUS_CHARACTER_LENGTH = 2;

    @Column(name = "password", nullable = false)
    private String value;

    public PlainPassword(final String value) {
        validateNull(value);
        validateLength(value.length());
        validateFormat(value);
        this.value = value;
    }

    private void validateNull(final String value) {
        if (Objects.isNull(value)) {
            throw new BusinessException("패스워드는 null일 수 없습니다.");
        }
    }

    private void validateLength(final int length) {
        if (length < PASSWORD_MINIMUM_LENGTH) {
            throw new BusinessException(String.format("패스워드 길이는 %d자 이상으로 입력해주세요.", PASSWORD_MINIMUM_LENGTH));
        }
    }

    private void validateFormat(final String value) {
        if (!isMatch(value)) {
            throw new BusinessException("패스워드 형식이 올바르지 않습니다.");
        }

        if (isContinuousSameCharacter(value)) {
            throw new BusinessException(
                    String.format("같은 문자는 연속적으로 %d회 이상 사용할 수 없습니다.", MAX_SAME_CONTINUOUS_CHARACTER_LENGTH + 1));
        }
    }

    private static boolean isMatch(final String value) {
        final Matcher matcher = PASSWORD_FORMAT_PATTERN.matcher(value);
        return matcher.matches();
    }

    private boolean isContinuousSameCharacter(final String value) {
        int index = 0;
        while (index < value.length() - MAX_SAME_CONTINUOUS_CHARACTER_LENGTH) {
            final int sameCharacterCount = getCountOfSameCharacter(value, index);
            if (sameCharacterCount > MAX_SAME_CONTINUOUS_CHARACTER_LENGTH) {
                return true;
            }
            index += MAX_SAME_CONTINUOUS_CHARACTER_LENGTH;
        }
        return false;
    }

    private static int getCountOfSameCharacter(final String value, final int startIndex) {
        final char startCharacter = value.charAt(startIndex);
        int sameCount = 1;
        int nextIndex = startIndex + 1;
        while (nextIndex <= startIndex + MAX_SAME_CONTINUOUS_CHARACTER_LENGTH) {
            if (value.charAt(nextIndex) == startCharacter) {
                sameCount++;
            }
            nextIndex++;
        }
        return sameCount;
    }
}
