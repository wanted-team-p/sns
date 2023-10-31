package com.wanted.sns.repository;

import com.wanted.sns.domain.Post;

public interface PostCustomRepository {
    Post findPostBySeq(long seq);
}
