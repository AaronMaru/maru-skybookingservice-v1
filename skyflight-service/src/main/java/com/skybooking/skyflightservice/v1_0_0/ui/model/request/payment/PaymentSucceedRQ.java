package com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentSucceedRQ {

    private String bookingCode;
    private String holderName;
    private String cardNumber;
    private String cvv;
    private String expiredDate;
    private String cardType;
    private String bankName;
    private String description;
    private int status = 1;
    private String pipiyStatus;
    private String transId;
    private String orderId;
    private String processorId;
    private String digest;
    private String method;
    private BigDecimal amount;
    private String currency;
    private Integer ipay88Status;
    private String authCode;
    private String signature;
    private String ipay88PaymentId;
    private String paymentCode;

}
