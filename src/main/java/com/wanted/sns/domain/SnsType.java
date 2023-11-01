package com.wanted.sns.domain;

import lombok.Getter;

@Getter
public enum SnsType {

    FACEBOOK("facebook","https://www.facebook.com/" ),

    TWITTER("twitter","https://www.twitter.com/"),

    INSTAGRAM("instagram","https://www.instagram.com/"),

    THREADS("threads","https://www.threads.com/");

    private final String value;

    private final String baseurl;

    SnsType(String value, String baseurl) {
        this.value = value;
        this.baseurl = baseurl;
    }

}
