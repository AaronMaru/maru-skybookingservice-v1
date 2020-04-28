package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDetailRS {

    private Integer id;
    private Integer bookingId;
    private String fullname;
    private String photo;
    private String notiType = "";
    private String tripType;
    private String title = "";
    private String description;
    private String notiIcon = "";
    private Date createdAt;

}
