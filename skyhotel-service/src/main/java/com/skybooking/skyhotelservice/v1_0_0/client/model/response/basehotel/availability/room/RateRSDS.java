package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import lombok.Data;

import java.util.List;

@Data
public class RateRSDS {
    private String rateKey;
    private Integer allotment;
    private String paymentType;
    private String boardCode;
    private String boardName;
    private PriceRSDS price;
    private CancellationRSDS cancellation;
    private List<PromotionRSDS> promotions;
}
