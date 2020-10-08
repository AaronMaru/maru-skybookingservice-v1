package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInfoRS {
    private BigDecimal amount;
    private String currency;
    private String cardHolder;
    private String cardNumber;
    private String bankName;
    private String paymentCode;
}
