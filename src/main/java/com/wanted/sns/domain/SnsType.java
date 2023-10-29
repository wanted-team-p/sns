package com.wanted.sns.domain;

import lombok.Getter;

@Getter
public enum SnsType {

  FACEBOOK("facebook"),
  TWITTER("twitter"),
  INSTAGRAM("instagram"),
  THREADS("threads");

  private final String value;

  SnsType(String value) {
    this.value = value;
  }

  public static SnsType find(String value) {
    for (SnsType snsType : SnsType.values()) {
      if (snsType.getValue().equals(value)) {
        return snsType;
      }
    }
    return null;
  }

}
