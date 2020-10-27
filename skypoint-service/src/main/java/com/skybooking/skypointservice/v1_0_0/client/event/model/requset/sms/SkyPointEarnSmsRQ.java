package com.skybooking.skypointservice.v1_0_0.client.event.model.requset.sms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkyPointEarnSmsRQ {
    private String phone;
    private String transactionFor;
    private BigDecimal amount;
    private String bookingId;

}
