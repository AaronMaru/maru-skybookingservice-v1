package com.skybooking.skypointservice.v1_0_0.ui.model.request.payment;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRQ {
    private BigDecimal amount;
    private String transactionFor;
    private String referenceCode;
    private String paymentMethod;
    private ContactInfo contactInfo;

}
