package com.wanted.sns.domain;

import jakarta.persistence.*;
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
        validateFormatPattern(value);
        this.value = value;
    }

    private void validateFormatPattern(final String value) {
        final Matcher matcher = EMAIL_FORMAT_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
