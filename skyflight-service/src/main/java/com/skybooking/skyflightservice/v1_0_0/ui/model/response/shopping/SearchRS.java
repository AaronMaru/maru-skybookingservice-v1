package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class SearchRS extends BaseRS {

    public SearchRS(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public SearchRS(HttpStatus status, Object data) {
        super(status, data);
    }

    public SearchRS(HttpStatus status, String message, Object data) {
        super(status, message, data);
    }

    public SearchRS(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
