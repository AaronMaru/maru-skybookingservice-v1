package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class PolicyRS extends BaseRS {

    public PolicyRS(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public PolicyRS(HttpStatus httpStatus, Object data) {
        super(httpStatus, data);
    }

    public PolicyRS(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message, data);
    }

    public PolicyRS(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
