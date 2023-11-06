package com.wanted.sns.repository;

import com.wanted.sns.domain.Order;
import com.wanted.sns.domain.Post;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.dto.StatisticRequest;
import com.wanted.sns.dto.StatisticResponse;
import com.wanted.sns.dto.ValidatedStatisticRequest;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = "classpath:h2/data.sql")
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("게시글 목록 조회 테스트")
    @Test
    public void getPostList() {
        String searchBy = "title";
        String search = "1";
        String hashtag = "해시태그1";
        String order = Order.ASC.getValue();
        String orderBy = "updatedAt";

        PostRequest postRequest = PostRequest.builder()
                .hashtag(hashtag)
                .order(order)
                .orderBy(orderBy)
                .searchBy(searchBy)
                .search(search)
                .build();
        List<PostResponse> result = postRepository.findAllPostBy(postRequest);

        SoftAssertions.assertSoftly(softAssertions -> {
            for (PostResponse response : result) {
                softAssertions.assertThat(response.getTitle())
                        .contains(search);
            }
        });
    }

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("통계 요청 테스트: 정렬 기준 date에 대해서 게시물 수 합산")
    @Test
    void aggregatePostCountSortByDateTest() {
        String countType = "post_count";
        String sortType = "date";
        String hashtag = "해시태그1";
        LocalDate end = LocalDate.now().plusDays(1L);

        StatisticRequest request = StatisticRequest.builder()
                .countType(countType)
                .sortType(sortType)
                .hashtag(hashtag)
                .end(end)
                .build();

        ValidatedStatisticRequest validatedRequest = new ValidatedStatisticRequest(request);
        List<StatisticResponse> result = postRepository.aggregateCount(validatedRequest);
        long resultedSum = 0;
        for (StatisticResponse response : result) {
            resultedSum += response.getCount();
        }

        List<Post> allPosts = postRepository.findAll();
        long noOfPosts = allPosts.size();
        Assertions.assertThat(resultedSum).isEqualTo(noOfPosts);
    }

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("통계 요청 테스트: 정렬 기준 date에 대해서 조회수 합산")
    @Test
    void aggregateViewCountSortByDateTest() {
        String countType = "view_count";
        String sortType = "date";
        String hashtag = "해시태그1";
        LocalDate end = LocalDate.now().plusDays(1L);

        StatisticRequest request = StatisticRequest.builder()
                .countType(countType)
                .sortType(sortType)
                .hashtag(hashtag)
                .end(end)
                .build();

        ValidatedStatisticRequest validatedRequest = new ValidatedStatisticRequest(request);
        List<StatisticResponse> result = postRepository.aggregateCount(validatedRequest);
        long resultedSum = 0;
        for (StatisticResponse response : result) {
            resultedSum += response.getCount();
        }

        List<Post> allPosts = postRepository.findAll();
        long sumOfViewCount = 0;
        for (Post post : allPosts) {
            sumOfViewCount += post.getViewCount();
        }
        Assertions.assertThat(resultedSum).isEqualTo(sumOfViewCount);
    }

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("통계 요청 테스트: 정렬 기준 date에 대해서 좋아요 수 합산")
    @Test
    void aggregateLikeCountSortByDateTest() {
        String countType = "like_count";
        String sortType = "date";
        String hashtag = "해시태그1";
        LocalDate end = LocalDate.now().plusDays(1L);

        StatisticRequest request = StatisticRequest.builder()
                .countType(countType)
                .sortType(sortType)
                .hashtag(hashtag)
                .end(end)
                .build();

        ValidatedStatisticRequest validatedRequest = new ValidatedStatisticRequest(request);
        List<StatisticResponse> result = postRepository.aggregateCount(validatedRequest);
        long resultedSum = 0;
        for (StatisticResponse response : result) {
            resultedSum += response.getCount();
        }

        List<Post> allPosts = postRepository.findAll();
        long sumOfLikeCount = 0;
        for (Post post : allPosts) {
            sumOfLikeCount += post.getViewCount();
        }
        Assertions.assertThat(resultedSum).isEqualTo(sumOfLikeCount);
    }

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("통계 요청 테스트: 정렬 기준 date에 대해서 공유 수 합산")
    @Test
    void aggregateShareCountSortByDateTest() {
        String countType = "share_count";
        String sortType = "date";
        String hashtag = "해시태그1";
        LocalDate end = LocalDate.now().plusDays(1L);

        StatisticRequest request = StatisticRequest.builder()
                .countType(countType)
                .sortType(sortType)
                .hashtag(hashtag)
                .end(end)
                .build();

        ValidatedStatisticRequest validatedRequest = new ValidatedStatisticRequest(request);
        List<StatisticResponse> result = postRepository.aggregateCount(validatedRequest);
        long resultedSum = 0;
        for (StatisticResponse response : result) {
            resultedSum += response.getCount();
        }

        List<Post> allPosts = postRepository.findAll();
        long sumOfShareCount = 0;
        for (Post post : allPosts) {
            sumOfShareCount += post.getShareCount();
        }
        Assertions.assertThat(resultedSum).isEqualTo(sumOfShareCount);
    }

    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @DisplayName("통계 요청 테스트: 정렬 기준 hour 결과 출력")
    @Test
    void aggregateShareCountSortByHourTest() {
        String countType = "post_count";
        String sortType = "hour";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.now().minusDays(5L);
        LocalDate end = LocalDate.now().plusDays(1L);

        StatisticRequest request = StatisticRequest.builder()
                .countType(countType)
                .sortType(sortType)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        ValidatedStatisticRequest validatedRequest = new ValidatedStatisticRequest(request);
        List<StatisticResponse> result = postRepository.aggregateCount(validatedRequest);
        for (StatisticResponse response : result) {
            System.out.println();
            System.out.println("response.getCreatedAt() = " + response.getCreatedAt());
            System.out.println("response.getCount() = " + response.getCount());
        }
    }

}
