package com.skybooking.skypointservice.v1_0_0.client.event.model.requset;

import lombok.Data;

@Data
public class NotificationRQ {
    private String bookingId;
    private String type;
}
