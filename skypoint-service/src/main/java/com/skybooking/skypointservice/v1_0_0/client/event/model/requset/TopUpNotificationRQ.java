package com.skybooking.skypointservice.v1_0_0.client.event.model.requset;

import lombok.Data;

@Data
public class TopUpNotificationRQ {
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String bookingId;
    private String type = "TOPUP_POINT";
}
