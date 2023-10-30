package com.wanted.sns.repository;

import com.wanted.sns.domain.Order;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void getPosts() {
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

}
