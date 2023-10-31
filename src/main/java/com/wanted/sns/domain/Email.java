package com.wanted.sns.domain;

import com.wanted.sns.exception.*;
import jakarta.persistence.*;
import java.util.*;
import java.util.regex.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    private static final String EMAIL_FORMAT_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_FORMAT_PATTERN = Pattern.compile(EMAIL_FORMAT_REGEX);

    @Column(name = "email", nullable = false)
    private String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new BusinessException("이메일은 null일 수 없습니다.");
        }

        if (!isMatch(value)) {
            throw new BusinessException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private static boolean isMatch(final String value) {
        final Matcher matcher = EMAIL_FORMAT_PATTERN.matcher(value);
        return matcher.matches();
    }
}
