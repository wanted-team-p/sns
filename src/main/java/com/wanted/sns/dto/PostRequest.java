package com.wanted.sns.dto;

import com.wanted.sns.domain.Order;
import lombok.Builder;

public record PostRequest(
        String hashtag, String type, String order, String orderBy,
        String searchBy, String search, Integer pageCount, Integer page) {
    @Builder
    public PostRequest {
        if (pageCount == null) {
            pageCount = 10;
        }

        if (page == null || page < 0) {
            page = 0;
        }

        if (order == null) {
            order = Order.DESC.getValue();
        }

        if (orderBy == null) {
            orderBy = "createdAt";
        }
    }

}
