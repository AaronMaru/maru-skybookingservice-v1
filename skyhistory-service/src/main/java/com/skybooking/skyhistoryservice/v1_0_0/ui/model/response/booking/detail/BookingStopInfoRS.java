package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

import java.util.Date;

@Data
public class BookingStopInfoRS {

    private Integer elapsedTime;
    private String locationCode;
    private Date departureDate;
    private Date arrivalDate;
    private Object duration;
    private String equipment;

}
