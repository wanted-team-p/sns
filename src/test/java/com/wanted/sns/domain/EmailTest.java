package com.wanted.sns.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

class EmailTest {

    @DisplayName("이메일값에 null이 들어온 경우, 예외를 발생한다.")
    @Test
    void nullEmail() {
        final String value = null;

        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이메일은 null일 수 없습니다.");
    }

    @DisplayName("이메일 형식이 올바르지 않은 경우, 예외를 발생한다.")
    @ValueSource(strings = {"", " ddd@naver.com", "abc@@naver.com", "abc@naver.co%", "abc@naver.commmmm"})
    @ParameterizedTest
    void invalidEmailFormat(String value) {
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일을 정상적으로 생성한다.")
    @Test
    void create() {
        final String value = "abc1234@naver.com";

        final Email email = new Email(value);

        assertThat(email.getValue()).isEqualTo(value);
    }
}
