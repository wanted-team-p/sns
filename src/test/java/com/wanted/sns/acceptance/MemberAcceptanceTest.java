package com.wanted.sns.acceptance;

import static org.assertj.core.api.Assertions.*;

import com.wanted.sns.dto.*;
import io.restassured.*;
import io.restassured.response.*;
import java.time.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.server.*;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemberAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("회원가입에 성공하면, 상태코드 201을 반환한다.")
    @Test
    void signUp() {
        final SignUpRequest request = new SignUpRequest("accountName", "abcd3@naver.com", "abcABC1234!",
                LocalDate.of(2000, 1, 1), "hanul", "01012545678");

        final Response response = RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/members");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("올바르지 않은 정보로 회원가입을 요청하면, 상태코드 400을 반환한다.")
    @Test
    void signUpWithIncorrectInformation() {
        final String invalidPassword = "abc";
        final SignUpRequest request = new SignUpRequest("accountName", "abcd3@naver.com", invalidPassword,
                LocalDate.of(2000, 1, 1), "hanul", "01012545678");

        final Response response = RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/members");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
