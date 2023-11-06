package com.wanted.sns.dto;

import lombok.Getter;

@Getter
public class StatisticResponse {

    private String createdAt;

    private long count;

    public StatisticResponse(final String createdAt, final long count) {
        this.createdAt = createdAt;
        this.count = count;
    }

}
