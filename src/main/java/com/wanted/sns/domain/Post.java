package com.wanted.sns.domain;

import com.wanted.sns.dto.PostResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Post {

    @OneToMany(mappedBy = "post")
    List<HashtagMapping> hashtagMappingList;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long seq;
    @Enumerated(EnumType.STRING)
    private SnsType type;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int shareCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public PostResponse toDTO() {
        increaseViewCount();
        return new PostResponse(this);
    }

    public void increaseViewCount() {
        viewCount++;
    }

}
