package com.wanted.sns.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CountTypeTest {

    @DisplayName(value = "post_count 문자열에 대한 find 메소드 테스트")
    @Test
    void postCountFindTest() {
        String givenString = "post_count";

        CountType foundCountType = CountType.find(givenString);

        Assertions.assertThat(foundCountType).isEqualTo(CountType.POST);
    }

    @DisplayName(value = "view_count 문자열에 대한 find 메소드 테스트")
    @Test
    void viewCountFindTest() {
        String givenString = "view_count";

        CountType foundCountType = CountType.find(givenString);

        Assertions.assertThat(foundCountType).isEqualTo(CountType.VIEW);
    }

    @DisplayName(value = "like_count 문자열에 대한 find 메소드 테스트")
    @Test
    void likeCountFindTest() {
        String givenString = "like_count";

        CountType foundCountType = CountType.find(givenString);

        Assertions.assertThat(foundCountType).isEqualTo(CountType.LIKE);
    }

    @DisplayName(value = "share_count 문자열에 대한 find 메소드 테스트")
    @Test
    void shareCountFindTest() {
        String givenString = "share_count";

        CountType foundCountType = CountType.find(givenString);

        Assertions.assertThat(foundCountType).isEqualTo(CountType.SHARE);
    }

    @DisplayName(value = "잘못된 문자열에 대한 find 메소드 테스트")
    @ValueSource(strings = {"Share_count", "share", "count", "dislike", "wanted"})
    @ParameterizedTest
    void invalidStringFindTest(String givenString) {
        CountType convertedValue = CountType.find(givenString);

        Assertions.assertThat(convertedValue).isNull();
    }

}