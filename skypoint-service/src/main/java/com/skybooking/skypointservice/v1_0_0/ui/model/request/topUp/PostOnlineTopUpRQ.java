package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import lombok.Data;

@Data
public class PostOnlineTopUpRQ {
    private String transactionCode;
    private String paymentStatus;
}
