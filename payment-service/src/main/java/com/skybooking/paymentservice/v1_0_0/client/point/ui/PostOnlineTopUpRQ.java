package com.skybooking.paymentservice.v1_0_0.client.point.ui;

import lombok.Data;

@Data
public class PostOnlineTopUpRQ {
    private String transactionCode;
    private String paymentStatus;
}
