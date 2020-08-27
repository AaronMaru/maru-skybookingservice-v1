package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import lombok.Data;

@Data
public class NotificationRQ {

    private Integer bookingId;
    private String type;
    private String script;

}
