package com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class BookingCancelRS extends BaseRS {

    public BookingCancelRS(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message, data);
    }
}
