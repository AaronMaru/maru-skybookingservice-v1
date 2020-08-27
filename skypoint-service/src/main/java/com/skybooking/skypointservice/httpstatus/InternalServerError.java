package com.skybooking.skypointservice.httpstatus;

public class InternalServerError extends RuntimeException {

    private final String message;
    private final Object data;

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
