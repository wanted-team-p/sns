package com.wanted.sns.dto;

import com.wanted.sns.domain.CountType;
import com.wanted.sns.domain.SortType;
import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatisticRequest {

    @NotNull(message = "조회하려는 통계 유형을 정확히 입력해주세요.")
    private CountType countType;

    @NotNull(message = "date 또는 hour가 입력되어야 합니다.")
    private SortType sortType;

    private String hashtag;

    private LocalDate start;

    private LocalDate end;

    @Builder
    @ConstructorProperties({"value", "sort_type", "hashtag", "start", "end"})
    public StatisticRequest(final String countType, final String sortType, final String hashtag,
                            final LocalDate start, final LocalDate end) {
        this.countType = CountType.find(countType);
        this.sortType = SortType.find(sortType);
        this.hashtag = hashtag;
        this.start = start;
        this.end = end;
    }

}
