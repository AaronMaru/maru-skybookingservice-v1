package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification;


import lombok.Data;

import java.util.Date;

@Data
public class NotificationTO {

    private Integer id;
    private Integer bookingId;
    private String bookingCode;
    private String fullname;
    private String photo;
    private String notiType;
    private String tripType;
    private String title;
    private String description;
    private String notiIcon;
    private Date createdAt;

}
