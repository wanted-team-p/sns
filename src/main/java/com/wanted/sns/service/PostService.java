package com.wanted.sns.service;

import com.wanted.sns.domain.Post;
import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostResponse getPost(long seq) {
        Post post = postRepository.findPostBySeq(seq);

        post.increaseViewCount();

        return post.toDTO();
    }
}
