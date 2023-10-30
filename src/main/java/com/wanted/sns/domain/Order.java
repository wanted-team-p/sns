package com.wanted.sns.domain;

import lombok.Getter;

@Getter
public enum Order {

    ASC("asc"), DESC("desc");

    private final String value;

    Order(String value) {
        this.value = value;
    }

}
