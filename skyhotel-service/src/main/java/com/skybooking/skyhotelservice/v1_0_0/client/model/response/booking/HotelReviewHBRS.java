package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability.ReviewType;
import lombok.Data;

@Data
public class HotelReviewHBRS {
    private Number rate;
    private Integer reviewCount;
    private ReviewType type;
}
