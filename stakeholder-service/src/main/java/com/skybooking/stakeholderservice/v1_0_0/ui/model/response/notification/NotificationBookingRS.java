package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationBookingRS {

    private String departureCity;
    private String arrivalCity;
    private Date departureDate;

}