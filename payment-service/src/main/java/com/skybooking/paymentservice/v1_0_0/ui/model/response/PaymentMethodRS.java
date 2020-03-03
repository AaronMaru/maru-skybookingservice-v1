package com.skybooking.paymentservice.v1_0_0.ui.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentMethodRS {

    private String type;
    private String code;
    private String method;
    private BigDecimal percentage;
    private String imageUrl;

    public PaymentMethodRS(String type, String code, String method, BigDecimal percentage, String imageUrl) {
        this.type = type;
        this.code = code;
        this.method = method;
        this.percentage = percentage;
        this.imageUrl = imageUrl;
    }
}
