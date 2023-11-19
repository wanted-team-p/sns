package com.wanted.sns.dto;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidatedStatisticRequestTest {

    @DisplayName("validatedStatisticRequest 생성자 정상 동작 테스트: 조회 시작일자와 종료일자가 같은 경우")
    @Test
    void createValidatedStatisticRequestTest1() {
        String countTypeInString = "view_count";
        String sortTypeInString = "date";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-10-22");
        LocalDate end = LocalDate.parse("2023-10-22");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatCode(() -> new ValidatedStatisticRequest(given))
                .doesNotThrowAnyException();
    }

    @DisplayName("validatedStatisticRequest 생성자 정상 동작 테스트: 조회 타입 hour의 최대 조회 기간에 대한 정상 동작 여부 확인")
    @Test
    void createValidatedStatisticRequestTest2() {
        String countTypeInString = "view_count";
        String sortTypeInString = "hour";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-10-20");
        LocalDate end = LocalDate.parse("2023-10-27");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatCode(() -> new ValidatedStatisticRequest(given))
                .doesNotThrowAnyException();
    }

    @DisplayName("validatedStatisticRequest 생성자 정상 동작 테스트: 조회 타입 date의 최대 조회 기간에 대한 정상 동작 여부 확인")
    @Test
    void createValidatedStatisticRequestTest3() {
        String countTypeInString = "view_count";
        String sortTypeInString = "date";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-09-27");
        LocalDate end = LocalDate.parse("2023-10-27");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatCode(() -> new ValidatedStatisticRequest(given))
                .doesNotThrowAnyException();
    }

    @DisplayName("validatedStatisticRequest 생성자 예외 테스트: 조회 종료일자가 조회 시작일자보다 빠른 경우")
    @Test
    void createValidatedStatisticRequestExceptionTest1() {
        String countTypeInString = "view_count";
        String sortTypeInString = "date";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-10-22");
        LocalDate end = LocalDate.parse("2023-10-21");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatThrownBy(() -> new ValidatedStatisticRequest(given))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("validatedStatisticRequest 생성자 예외 테스트: 정렬 유형 hour의 최대 조회 가능 기간을 초과한 경우")
    @Test
    void createValidatedStatisticRequestExceptionTest2() {
        String countTypeInString = "view_count";
        String sortTypeInString = "hour";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-10-20");
        LocalDate end = LocalDate.parse("2023-10-28");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatThrownBy(() -> new ValidatedStatisticRequest(given))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("validatedStatisticRequest 생성자 예외 테스트: 정렬 유형 date의 최대 조회 가능 기간을 초과한 경우")
    @Test
    void createValidatedStatisticRequestExceptionTest3() {
        String countTypeInString = "view_count";
        String sortTypeInString = "date";
        String hashtag = "해시태그1";
        LocalDate start = LocalDate.parse("2023-09-27");
        LocalDate end = LocalDate.parse("2023-10-28");

        StatisticRequest given = StatisticRequest.builder()
                .countType(countTypeInString)
                .sortType(sortTypeInString)
                .hashtag(hashtag)
                .start(start)
                .end(end)
                .build();

        Assertions.assertThatThrownBy(() -> new ValidatedStatisticRequest(given))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
