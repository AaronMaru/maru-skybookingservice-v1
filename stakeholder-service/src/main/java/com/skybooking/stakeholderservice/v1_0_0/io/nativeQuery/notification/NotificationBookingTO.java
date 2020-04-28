package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification;


import lombok.Data;

import java.util.Date;

@Data
public class NotificationBookingTO {

    private String departureCity;
    private String arrivalCity;
    private Date departureDate;

}
