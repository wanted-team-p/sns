package com.wanted.sns.domain;

import jakarta.persistence.*;
import lombok.*;

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
