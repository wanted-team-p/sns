package com.wanted.sns.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class HashtagMapping {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_post", referencedColumnName = "seq")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_hashtag", referencedColumnName = "seq")
    private Hashtag hashtag;

}
