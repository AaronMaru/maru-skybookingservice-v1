package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInfo {
    private String payTo = "SKYBOOKING";
    private String paymentOf;
    private String paymentId;
    private String cardNumber = "";
    private String paymentMethod = "";
}
