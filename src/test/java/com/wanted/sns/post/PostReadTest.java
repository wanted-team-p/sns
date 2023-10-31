package com.wanted.sns.post;

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

    @DisplayName("게시글 목록 조회 테스트")
    @Nested
    public class getPostList{
        @DisplayName("기본 최신순 목록 조회")
        @Test
        public void getDefault(){
            RestAssured.when().get("/post")
                    .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                    .log().all();
        }

        @DisplayName("해시태그 검색")
        @Test
        public void searchHashtag(){
            RestAssured.when().get("/post?hashtag=해시태그1")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .log().all();
        }

        @DisplayName("제목 + 내용 검색")
        @Test
        public void searchBy(){
            RestAssured.when().get("/post?searchBy=title,content&search=법률")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .log().all();
        }

        @DisplayName("좋아요순으로 정렬")
        @Test
        public void orderBy(){
            RestAssured.when().get("/post?orderBy=likeCount&order=desc")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .log().all();
        }
    }

}
