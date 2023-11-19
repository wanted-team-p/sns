package com.wanted.sns.repository;

import com.wanted.sns.domain.Post;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.dto.StatisticResponse;
import com.wanted.sns.dto.ValidatedStatisticRequest;
import java.util.List;

public interface PostCustomRepository {
    Post findPostBySeq(long seq);
    
    List<PostResponse> findAllPostBy(PostRequest postRequest);

    List<StatisticResponse> aggregateCount(ValidatedStatisticRequest statisticRequest);
}
