package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import com.skybooking.skyhotelservice.constant.BookingStatus;
import lombok.Data;

@Data
public class ReserveDSRS {
    private String code;
    private Integer hotelCode;
    private String consumerCode;
    private String checkIn;
    private String checkOut;
    private BookingStatus status = BookingStatus.PRECONFIRMED;
}
