package com.wanted.sns.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class EncryptorTest {

    @DisplayName("sha256 방식으로 암호화한다.")
    @Test
    void encrypt() {
        final Encryptor encryptor = new SHA256Encryptor();
        final String password = "abcABC1234!";
        final String expected = encryptor.encrypt(password);

        final String actual = encryptor.encrypt(password);

        assertThat(actual).isEqualTo(expected);
    }
}
