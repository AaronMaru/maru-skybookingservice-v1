package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import com.skybooking.skyhotelservice.constant.ReviewTypeConstant;
import lombok.Data;

@Data
public class ReviewRQDS {

    private String type = ReviewTypeConstant.HOTELBEDS;
    private Integer minRate = 0;
    private Integer maxRate = 5;
    private Integer minReviewCount = 1;

}
