package com.wanted.sns.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SnsApiRequestTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach(){
        RestAssured.port = port;
    }

    @DisplayName("좋아요 api 호출 테스트")
    @Nested
    public class callLikeApi{

        @DisplayName("성공")
        @ValueSource(longs = {1,2})
        @ParameterizedTest
        public void success(long seq){
            RestAssured.when().post("/post/like/"+seq)
                    .then().assertThat().statusCode(HttpStatus.OK.value());
        }

        @DisplayName("없는 게시글 실패")
        @ValueSource(longs = {9999,99999})
        @ParameterizedTest
        public void fail(long seq){
            RestAssured.when().post("/post/like/"+seq)
                    .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @DisplayName("공유 api 호출 테스트")
    @Nested
    public class callShareApi{

        @DisplayName("성공")
        @ValueSource(longs = {1,2})
        @ParameterizedTest
        public void success(long seq){
            RestAssured.when().post("/post/like/"+seq)
                    .then().assertThat().statusCode(HttpStatus.OK.value());
        }

        @DisplayName("없는 게시글 실패")
        @ValueSource(longs = {9999,99999})
        @ParameterizedTest
        public void fail(long seq){
            RestAssured.when().post("/post/like/"+seq)
                    .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
        }
    }
}
