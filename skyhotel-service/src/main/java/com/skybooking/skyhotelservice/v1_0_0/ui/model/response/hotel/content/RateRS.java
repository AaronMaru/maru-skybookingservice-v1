package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RateRS {
    private String rateKey;
    private Integer allotment;
    private PaymentType paymentType;
    private String boardCode;
    private String boardName;
    private PriceRS price;
    private CancellationRS cancellation;
    private List<PromotionRS> promotions = new ArrayList<>();

    public static enum PaymentType {
        PREPAY("AT_WEB"),
        POSTPAY("AT_HOTEL");

        public String getValue() {
            return value;
        }

        private String value;

        PaymentType(String value) {
            this.value = value;
        }

        public static PaymentType getByValue(String value) {
            if (PaymentType.POSTPAY.value.equals(value))
                return PaymentType.POSTPAY;
            return PaymentType.PREPAY;
        }
    }
}
