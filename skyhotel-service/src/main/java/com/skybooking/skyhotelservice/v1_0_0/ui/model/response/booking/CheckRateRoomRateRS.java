package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import com.skybooking.skyhotelservice.constant.RateType;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.CancellationPolicyRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RateRS;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckRateRoomRateRS {
    private String rateKey;
    private String rateClass;

    @Enumerated(EnumType.STRING)
    private RateType rateType;

    private BigDecimal net = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private Number discountPCT;
    private Number sellingRate;
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
    private CheckRateRateBreakDownRS rateBreakDown;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private String childrenAges;
    private Number rateup;
    private String brand;
    private boolean resident;
    private List<CancellationPolicyRS> cancellationPolicies;
    private CheckRateRateTaxesRS taxes;
    private List<RatePromotionRS> promotions;
    private List<RateOfferRS> offers;
}
