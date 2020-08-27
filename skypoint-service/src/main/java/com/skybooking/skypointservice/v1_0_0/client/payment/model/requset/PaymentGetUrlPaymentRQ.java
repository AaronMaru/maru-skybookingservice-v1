package com.skybooking.skypointservice.v1_0_0.client.payment.model.requset;

import lombok.Data;

@Data
public class PaymentGetUrlPaymentRQ {
    private String productType;
    private String bookingCode;
    private String paymentCode;
}
