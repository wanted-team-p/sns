package com.wanted.sns.domain;

import lombok.Getter;

@Getter
public enum SortType {

    DATE("date", 30L),
    HOUR("hour", 7L);

    public final String value;
    public final long maximumTimePeriodInDays;

    SortType(String value, long maximumTimePeriodInDays) {
        this.value = value;
        this.maximumTimePeriodInDays = maximumTimePeriodInDays;
    }

    public static SortType find(String value) {
        for (SortType sortType : SortType.values()) {
            if (sortType.getValue().equals(value)) {
                return sortType;
            }
        }
        return null;
    }

}
