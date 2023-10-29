package com.wanted.sns.support;

import java.security.*;
import org.springframework.stereotype.*;

@Component
public class SHA256Encryptor implements Encryptor {

    @Override
    public String encrypt(final String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
