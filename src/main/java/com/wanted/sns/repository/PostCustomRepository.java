package com.wanted.sns.repository;

import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import java.util.List;

public interface PostCustomRepository {

    List<PostResponse> findAllPostBy(PostRequest postRequest);

}
