package com.wanted.sns.dto;

import com.wanted.sns.domain.HashtagMapping;
import com.wanted.sns.domain.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostResponse {

    long id;

    String title;

    String type;

    String content;

    int viewCount;

    int likeCount;

    int shareCount;

    List<String> hashtagList = new ArrayList<>();

    LocalDateTime updatedAt;

    LocalDateTime createdAt;

    public PostResponse(Post post) {
        id = post.getSeq();
        title = post.getTitle();
        type = post.getType().getValue();
        content = post.getContent().substring(0, 20);
        viewCount = post.getViewCount();
        likeCount = post.getLikeCount();
        shareCount = post.getShareCount();
        updatedAt = post.getUpdatedAt();
        createdAt = post.getCreatedAt();
        for (HashtagMapping hashtagMapping : post.getHashtagMappingList()) {
            hashtagList.add(hashtagMapping.getHashtag().getName());
        }
    }

}
