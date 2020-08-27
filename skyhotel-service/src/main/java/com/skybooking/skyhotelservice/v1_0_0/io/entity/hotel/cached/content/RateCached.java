package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RateCached implements Serializable {
    private String rateKey;
    private Integer allotment;
    private PaymentType paymentType;
    private String boardCode;
    private String boardName;
    private PriceCached price;
    private CancellationCached cancellation;
    private List<PromotionCached> promotions;

    private enum PaymentType {
        PREPAY,
        POSTPAY
    }
}
