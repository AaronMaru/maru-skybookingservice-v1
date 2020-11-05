package com.skybooking.backofficeservice.v1_0_0.ui.model.response.common;

import lombok.Data;

@Data
public class PaymentInfo {
    private String holderName;
    private String bankName;
    private String cardType;
    private String cardNumber;
    private String paymentType;
    private String paymentMethod;
}
