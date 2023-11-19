package com.wanted.sns.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SortTypeTest {

    @DisplayName(value = "date 문자열에 대한 find 메소드 테스트")
    @Test
    void dateStringFindTest() {
        String givenString = "date";

        SortType foundSortType = SortType.find(givenString);

        Assertions.assertThat(foundSortType).isEqualTo(SortType.DATE);
    }

    @DisplayName(value = "hour 문자열에 대한 find 메소드 테스트")
    @Test
    void hourStringFindTest() {
        String givenString = "hour";

        SortType foundSortType = SortType.find(givenString);

        Assertions.assertThat(foundSortType).isEqualTo(SortType.HOUR);
    }

    @DisplayName(value = "잘못된 문자열에 대한 find 메소드 테스트")
    @ValueSource(strings = {"day", "second", "", "wanted"})
    @ParameterizedTest
    void invalidStringFindTest(String giveString) {
        SortType convertedValue = SortType.find(giveString);

        Assertions.assertThat(convertedValue).isNull();
    }

}