package com.skybooking.skypointservice.v1_0_0.client.event.model.requset.sms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkyPointTopUpSuccessSmsRQ {
    private BigDecimal amount;
    private String phone;
    private String fullName;
    private BigDecimal earnAmount;
    private String transactionId;
    private String transactionDate;
}
