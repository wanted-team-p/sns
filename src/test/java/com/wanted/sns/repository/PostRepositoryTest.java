package com.wanted.sns.repository;

import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

  @Autowired
  PostRepository postRepository;

  @Test
  public void getPosts(){
    PostRequest postRequest = PostRequest.builder()
        .hashtag("해시태그1")
        .order("ASC")
        .orderBy("updatedAt")
        .searchBy("title")
        .search("1")
        .build();
    List<PostResponse> posts = postRepository.findAllPostBy(postRequest);
    System.out.println(posts.size());
  }
}
