package com.wanted.sns.support;

import java.util.concurrent.*;

public class RandomNumberGenerator {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private static final int RANDOM_BOUND = 10;
    private static final int GENERATION_LENGTH = 6;

    public static String generate() {
        StringBuilder temp = new StringBuilder();
        int index = 0;
        while (index++ < GENERATION_LENGTH) {
            temp.append(random.nextInt(RANDOM_BOUND));
        }
        return temp.toString();
    }
}
