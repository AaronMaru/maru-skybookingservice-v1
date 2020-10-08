package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RateTaxRSDS;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RateRSDS {
    private String rateKey;
    private String rateClass;
    private String rateType;
    private BigDecimal net;
    private BigDecimal discount;
    private Double discountPCT;
    private Integer allotment;
    private String paymentType;
    private Boolean packaging;
    private String boardCode;
    private String boardName;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private PriceRSDS price;
    private RateBreakDownRSDS rateBreakDown;
    private CancellationRSDS cancellation;
    private RateTaxRSDS taxes;
    private List<PromotionRSDS> promotions;
    private List<RateOfferRSDS> offers;
}
