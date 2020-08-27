package com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability;

import lombok.Data;

@Data
public class Review {
    private ReviewType type;
    private Integer maxRate;
    private Integer minReviewCount;
}
