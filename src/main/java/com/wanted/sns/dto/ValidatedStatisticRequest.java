package com.wanted.sns.dto;

import com.wanted.sns.domain.CountType;
import com.wanted.sns.domain.SortType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class ValidatedStatisticRequest {

    private CountType countType;

    private SortType sortType;

    private String hashtag;

    private LocalDateTime start;

    private LocalDateTime end;

    public ValidatedStatisticRequest(StatisticRequest statisticRequest) throws IllegalArgumentException {
        this.countType = statisticRequest.getCountType();
        this.sortType = statisticRequest.getSortType();
        this.hashtag = statisticRequest.getHashtag();
        this.start = setupStartDate(statisticRequest.getStart());
        this.end = setupEndDate(statisticRequest.getEnd());
        validateStartAndEndDate();
    }

    private LocalDateTime setupStartDate(LocalDate start) {
        if (start == null) {
            return LocalDateTime.now()
                    .minusWeeks(1L)
                    .truncatedTo(ChronoUnit.DAYS);
        }
        return LocalDateTime
                .of(start, LocalTime.of(0, 0));
    }

    private LocalDateTime setupEndDate(LocalDate end) {
        if (end == null) {
            return LocalDateTime.now()
                    .truncatedTo(ChronoUnit.DAYS);
        }
        return LocalDateTime
                .of(end, LocalTime.of(0, 0));
    }

    private void validateStartAndEndDate() throws IllegalArgumentException {
        boolean startIsAfterEnd = this.start.isAfter(this.end);
        if (startIsAfterEnd) {
            throw new IllegalArgumentException("조회 시작일은 조회 종료일 이후 시점이 될 수 없습니다.");
        }

        boolean isMaximumTimePeriodExceeded = this.end.isAfter(
                this.start.plusDays(this.sortType.maximumTimePeriodInDays));
        if (isMaximumTimePeriodExceeded) {
            throw new IllegalArgumentException(
                    String.format("정렬 기준이 %s인 경우 조회 기간은 최대 %d일까지 가능합니다.", this.sortType.value,
                            this.sortType.maximumTimePeriodInDays)
            );
        }
    }

}
