package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;


import com.skybooking.skyhotelservice.constant.RateType;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RateRS;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRoomRateHBRS {
    private String rateKey;
    private String rateClass;
    private RateType rateType;
    private BigDecimal net = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private Number discountPCT;
    private BigDecimal sellingRate;
    private Number hotelSellingRate;
    private String hotelCurrency;
    private boolean hotelMandatory;
    private Integer allotment;
    private BigDecimal commission = BigDecimal.ZERO;
    private Number commissionVAT;
    private Number commissionPCT;
    private String rateCommentsId;
    private String rateComments;
    private RateRS.PaymentType paymentType;
    private boolean packaging;
    private String boardCode;
    private String boardName;
    private RateBreakDownHBRS rateBreakDown;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private String childrenAges;
    private Number rateup;
    private String brand;
    private boolean resident;
    private List<CancellationPolicyHBRS> cancellationPolicies;
    private RateTaxesHBRS taxes;
    private List<RatePromotionHBRS> promotions;
    private List<RateOfferHBRS> offers;
}
