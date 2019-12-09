package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class FilterListRS extends BaseRS {

    public FilterListRS(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public FilterListRS(HttpStatus httpStatus, Object data) {
        super(httpStatus, data);
    }

    public FilterListRS(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message, data);
    }

    public FilterListRS(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
