package com.wanted.sns.domain;

import lombok.Getter;

@Getter
public enum CountType {

    POST("post_count", "seq"),
    VIEW("view_count", "viewCount"),
    LIKE("like_count", "likeCount"),
    SHARE("share_count", "shareCount");

    public final String value;
    public final String target;

    CountType(String value, String target) {
        this.value = value;
        this.target = target;
    }

    public static CountType find(String value) {
        for (CountType countType : CountType.values()) {
            if (countType.getValue().equals(value)) {
                return countType;
            }
        }
        return null;
    }

}
