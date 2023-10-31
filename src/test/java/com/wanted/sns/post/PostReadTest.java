package com.wanted.sns.post;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostReadTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach(){
        RestAssured.port = port;
    }

    @DisplayName("게시글 상세 조회 테스트")
    @Nested
    public class getPostList{

        @DisplayName("성공")
        @Test
        public void success(){
            int viewCount = 10;

            RestAssured.when().get("/post/1")
                    .then().assertThat().body("viewCount", equalTo(++viewCount))
                    .log().all();

            RestAssured.when().get("/post/1")
                    .then().assertThat().body("viewCount", equalTo(++viewCount))
                    .log().all();
        }

        @DisplayName("없는 게시글 조회")
        @Test
        public void fail(){
            RestAssured.when().get("/post/10")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .log().all();
        }
    }

}