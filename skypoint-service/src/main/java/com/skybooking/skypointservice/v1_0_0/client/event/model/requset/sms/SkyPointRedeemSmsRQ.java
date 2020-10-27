package com.skybooking.skypointservice.v1_0_0.client.event.model.requset.sms;

import lombok.Data;

@Data
public class SkyPointRedeemSmsRQ {
    private String phone;
    private String transactionFor;
    private String bookingId;
}
