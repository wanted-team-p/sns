package com.wanted.sns.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Hashtag {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long seq;

  private String name;

}
