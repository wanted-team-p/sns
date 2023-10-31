package com.wanted.sns.service;

import static org.assertj.core.api.Assertions.*;

import com.wanted.sns.domain.*;
import com.wanted.sns.dto.*;
import com.wanted.sns.exception.*;
import java.time.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.jdbc.*;

@Sql("classpath:truncate.sql")
@SpringBootTest
class VerificationServiceTest {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private MemberRepository memberRepository;
    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(
                new Member("memberId", new Email("abc@naver.com"), "abcABC1234!", LocalDate.of(2000, 1, 1), "hanul",
                        "01012345678", MemberRole.GUEST));
    }

    @DisplayName("승인 코드를 생성한다.")
    @Test
    void create() {
        assertThatNoException()
                .isThrownBy(() -> verificationService.create(member.getId()));
    }

    @DisplayName("승인 요청을 할 때")
    @Nested
    class verifyTest {

        @DisplayName("인증 가능 시간이 초과된 경우, 예외를 발생한다.")
        @Test
        void expireTime() {
            verificationCodeRepository.save(new VerificationCode(1L, "123456", 0, member.getId()));
            verificationService.checkCode(member.getId(), new VerifyRequest("123456"));
        }

        @DisplayName("최대 인증 횟수를 초과한 경우, 예외를 발생한다.")
        @Test
        void overMaxCount() {
            final int maxCount = 5;
            verificationCodeRepository.save(new VerificationCode(1L, "123456", maxCount, member.getId()));

            assertThatThrownBy(() -> verificationService.checkCode(member.getId(), new VerifyRequest("123456")))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("최대 인증 횟수를 초과했습니다. 인증코드를 재발급해주세요.");
        }

        @DisplayName("승인 코드가 올바르지 않은 경우, 인증 횟수를 1 증가하고, 예외를 발생한다.")
        @Test
        void incorrectCode() {
            verificationCodeRepository.save(new VerificationCode("123456", member.getId()));
            final VerifyRequest invalidRequest = new VerifyRequest("000000");
            final int expectedCount = 1;

            verificationService.checkCode(member.getId(), invalidRequest);
            final VerificationCode verificationCode = verificationCodeRepository.findByMemberId(member.getId()).get();
            final int actualCount = verificationCode.getCount();

            assertThat(actualCount).isEqualTo(expectedCount);
        }

        @DisplayName("승인 코드가 올바르다면, 승인을 완료하고 Role은 Member로 변경된다.")
        @Test
        void correctCode() {
            final String code = "123456";
            verificationCodeRepository.save(new VerificationCode(code, member.getId()));
            final VerifyRequest request = new VerifyRequest(code);

            verificationService.checkCode(member.getId(), request);
            final MemberRole actualRole = memberRepository.findById(member.getId()).get()
                    .getRole();

            assertThat(actualRole).isEqualTo(MemberRole.MEMBER);
        }
    }
}
