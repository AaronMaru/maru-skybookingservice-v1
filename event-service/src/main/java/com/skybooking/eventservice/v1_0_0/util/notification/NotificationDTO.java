package com.skybooking.eventservice.v1_0_0.util.notification;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long stakeholderUserId;
    private Long stakeholderCompanyId;
    private Integer bookingId;
    private String type;
    private String script;

}
