package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class FlightDetailRS extends BaseRS {

    public FlightDetailRS(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public FlightDetailRS(HttpStatus httpStatus, Object data) {
        super(httpStatus, data);
    }

    public FlightDetailRS(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message, data);
    }

    public FlightDetailRS(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
