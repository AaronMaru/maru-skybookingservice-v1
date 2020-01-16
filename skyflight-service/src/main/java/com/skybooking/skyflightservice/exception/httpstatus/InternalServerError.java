package com.skybooking.skyflightservice.exception.httpstatus;

public class InternalServerError extends RuntimeException {

    private String message;
    private Object data;

    public InternalServerError(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
