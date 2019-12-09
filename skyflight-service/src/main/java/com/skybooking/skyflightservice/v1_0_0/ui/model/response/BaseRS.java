package com.skybooking.skyflightservice.v1_0_0.ui.model.response;

import org.springframework.http.HttpStatus;

public class BaseRS {

    public Integer status;
    public String message;
    public Object data;

    public BaseRS(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = "";
        this.data = null;
    }

    public BaseRS(HttpStatus httpStatus, Object data) {
        this.status = httpStatus.value();
        this.message = "";
        this.data = data;
    }

    public BaseRS(HttpStatus httpStatus, String message, Object data) {
        this.status = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public BaseRS(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
        this.data = null;
    }
}
