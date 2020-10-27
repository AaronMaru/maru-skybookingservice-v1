package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RateRS {
    private String rateKey;
    private String rateClass;
    private String rateType;
    private Integer allotment;
    private PaymentType paymentType;
    private Boolean packaging;
    private String boardCode;
    private String boardName;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private PriceRS price;
    private CancellationRS cancellation;
//    private List<PromotionRS> promotions;
    private SpecialOfferRS offer;
//    private RateTaxesRS taxes;

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public static enum PaymentType implements Serializable {
        PREPAID("AT_WEB"),
        POSTPAID("AT_HOTEL");

        public String getValue() {
            return value;
        }

        private String value;

        PaymentType(String value) {
            this.value = value;
        }

        public static PaymentType getByValue(String value) {
            if (PaymentType.POSTPAID.value.equals(value))
                return PaymentType.POSTPAID;
            return PaymentType.PREPAID;
        }
    }
}
