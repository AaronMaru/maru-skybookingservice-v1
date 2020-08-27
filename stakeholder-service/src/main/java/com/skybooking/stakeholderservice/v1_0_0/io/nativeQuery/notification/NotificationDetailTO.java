package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification;


import lombok.Data;

import java.util.Date;

@Data
public class NotificationDetailTO {

    private Integer id;
    private Integer bookingId;
    private String fullname;
    private String photo;
    private String notiType;
    private String tripType;
    private String title;
    private String description;
    private int readable;
    private String notiIcon;
    private Integer skyuserId;
    private Integer companyId;
    private String replaceOne;
    private String replaceTwo;
    private Date createdAt;

}
