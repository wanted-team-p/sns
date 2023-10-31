package com.wanted.sns.domain;

import static org.assertj.core.api.Assertions.*;

import com.wanted.sns.exception.*;
import org.junit.jupiter.api.*;

class VerificationCodeTest {

    @DisplayName("인증코드가 null인 경우, 예외가 발생한다.")
    @Test
    void nullCode() {
        assertThatThrownBy(() -> new VerificationCode(null, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("인증코드는 null일 수 없습니다.");
    }

    @DisplayName("인증코드 길이가 올바르지 않은 경우, 예외가 발생한다.")
    @Test
    void invalidCodeLength() {
        final int codeLength = 6;

        assertThatThrownBy(() -> new VerificationCode("1234567", 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(String.format("인증코드는 %d 자리여야 합니다.", codeLength));
    }

    @DisplayName("승진 요청 횟수가 음수인 경우, 예외가 발생한다.")
    @Test
    void negativeCount() {
        final int negativeCount = -1;

        assertThatThrownBy(() -> new VerificationCode(1L, "123456", negativeCount, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("승인 요청 횟수는 음수가 될 수 없습니다.");
    }

    @DisplayName("승인 요청 횟수를 초과한 경우, 예외가 발생한다.")
    @Test
    void overMaxCount() {
        final int maxCount = 5;

        assertThatThrownBy(() -> new VerificationCode(1L, "123456", maxCount + 1, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(String.format("승인 요청 횟수는 최대 %d회를 넘길 수 없습니다.", maxCount));
    }
}
