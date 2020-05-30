package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class NotificationRS {

    private Integer id;
    private String bookingCode;
    private String fullname;
    private String photo;
    private String notiType = "";
    private String tripType;
    private String title = "";
    private String notiIcon = "";
    private Date createdAt;
    private String subject = "";
    private String subjectStatus = "";

    List<NotificationBookingRS> bookingLegs = new ArrayList<>();

}
