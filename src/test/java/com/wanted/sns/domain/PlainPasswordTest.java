package com.wanted.sns.domain;

import static org.assertj.core.api.Assertions.*;

import com.wanted.sns.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

class PlainPasswordTest {

    @DisplayName("패스워드가 null인 경우, 예외를 발생한다.")
    @Test
    void nullPassword() {
        final String nullValue = null;

        assertThatThrownBy(() -> new PlainPassword(nullValue))
                .isInstanceOf(BusinessException.class)
                .hasMessage("패스워드는 null일 수 없습니다.");
    }

    @DisplayName("패스워드 길이가 최소길이를 넘지 않는 경우, 예외를 발생한다.")
    @ValueSource(strings = {"", "1", "123", "1234", "123456789"})
    @ParameterizedTest
    void invalidLength(String value) {
        final int minimumLength = 10;

        assertThatThrownBy(() -> new PlainPassword(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(String.format("패스워드 길이는 %d자 이상으로 입력해주세요.", minimumLength));
    }

    @DisplayName("패스워드 형식이 올바르지 않은 경우, 예외를 발생한다.")
    @ValueSource(strings = {"abcdefghij", "abcdefghi1", "aAaAaAaAaAaAaA123", "a@a@a@a@a@a@"})
    @ParameterizedTest
    void invalidFormat(String value) {
        assertThatThrownBy(() -> new PlainPassword(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage("패스워드 형식이 올바르지 않습니다.");
    }

    @DisplayName("동일한 문자가 3회 이상 연속된 경우, 예외를 발생한다.")
    @Test
    void continuousSameCharacter() {
        final String value = "aaaBcdefghi@1234";
        final int maxSameContinuousLength = 2;

        assertThatThrownBy(() -> new PlainPassword(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(String.format("같은 문자는 연속적으로 %d회 이상 사용할 수 없습니다.", maxSameContinuousLength + 1));
    }

    @DisplayName("패스워드를 정상적으로 생성한다.")
    @Test
    void create() {
        final String value = "ABCabc123!";

        final PlainPassword plainPassword = new PlainPassword(value);

        assertThat(plainPassword.getValue()).isEqualTo(value);
    }
}
