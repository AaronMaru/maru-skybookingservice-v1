package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import lombok.Data;

@Data
public class NotificationTopUpRQ {

    private Long stakeholderUserId;
    private Long stakeholderCompanyId;
    private Integer bookingId;
    private String type;
    private String script;

}
