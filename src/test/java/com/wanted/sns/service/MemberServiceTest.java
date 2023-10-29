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
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입할 때")
    @Nested
    class signUpTest {

        @DisplayName("요청한 정보가 올바르다면, 회원가입을 완료한다.")
        @Test
        void signUp() {
            final SignUpRequest request = new SignUpRequest("memberId", "abc@naver.com", "abcABC1234!",
                    LocalDate.of(2000, 1, 1), "hanul", "01012345678");

            assertThatNoException()
                    .isThrownBy(() -> memberService.signUp(request));
        }

        @DisplayName("요청한 이메일이 이미 존재하면, 예외가 발생한다.")
        @Test
        void duplicateEmail() {
            memberRepository.save(new Member("memberId", new Email("abc@naver.com"), "abcABC1234!",
                    LocalDate.of(2000, 1, 1), "hanul", "01012345678", MemberRole.GUEST));
            final SignUpRequest sameEmailRequest = new SignUpRequest("memberId2", "abc@naver.com", "abcABC1234!",
                    LocalDate.of(2000, 1, 1), "hanul2", "01098765432");

            assertThatThrownBy(() -> memberService.signUp(sameEmailRequest))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("이미 존재하는 이메일입니다.");
        }

        @DisplayName("요청한 전화번호가 이미 존재하면, 예외가 발생한다.")
        @Test
        void duplicatePhoneNumber() {
            memberRepository.save(new Member("memberId", new Email("abc@naver.com"), "abcABC1234!",
                    LocalDate.of(2000, 1, 1), "hanul", "01012345678", MemberRole.GUEST));
            final SignUpRequest samePhoneNumberRequest = new SignUpRequest("memberId2", "abc2@naver.com", "abcABC1234!",
                    LocalDate.of(2000, 1, 1), "hanul2", "01012345678");

            assertThatThrownBy(() -> memberService.signUp(samePhoneNumberRequest))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("이미 존재하는 전화번호입니다.");
        }
    }
}
