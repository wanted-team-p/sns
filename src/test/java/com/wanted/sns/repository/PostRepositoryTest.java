package com.wanted.sns.repository;

import com.wanted.sns.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @ValueSource(longs = {1})
    @ParameterizedTest
    public void findPostDetail(long seq) {
        Post post = postRepository.findPostBySeq(seq);
        int beforeViewCount = post.getViewCount();

        post.increaseViewCount();

        int afterViewCount = postRepository.findPostBySeq(seq).getViewCount();

        Assertions.assertThat(++beforeViewCount).isEqualTo(afterViewCount);
    }
}
