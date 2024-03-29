package com.skybooking.paymentservice.v1_0_0.client.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPaymentTransactionRQ {

    private String bookingCode;
    private String holderName;
    private String cardNumber;
    private String cvv;
    private String expiredDate;
    private String cardType;
    private String bankName;
    private String description;
    private String pipayStatus;
    private String transId;
    private String orderId;
    private String processorId;
    private String digest;
    private String method;
    private BigDecimal amount;
    private String currency;
    private int ipay88Status;
    private String authCode;
    private String signature;
    private String ipay88PaymentId;
    private String paymentCode;

}
