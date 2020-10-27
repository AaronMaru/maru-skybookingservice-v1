package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RateRS;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RateCached implements Serializable {
    private String rateKey;
    private String rateClass;
    private String rateType;
    private Integer allotment;
    private RateRS.PaymentType paymentType;
    private Boolean packaging;
    private String boardCode;
    private String boardName;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private PriceCached price;
    private CancellationCached cancellation;
    private List<PromotionCached> promotions;
    private RateTaxesCached taxes;
    private RateBreakDownCached rateBreakDown;
    private SpecialOfferCached offer;
    private List<RateOfferCached> offers;

}
