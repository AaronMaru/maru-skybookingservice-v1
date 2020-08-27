package com.skybooking.skyhotelservice.v1_0_0.client.model.request.recommend;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RecommendHotelRQDS {

    List<String> destinations;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
