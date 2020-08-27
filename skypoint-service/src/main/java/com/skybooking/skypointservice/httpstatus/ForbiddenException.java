package com.skybooking.skypointservice.httpstatus;

public class ForbiddenException extends RuntimeException {

    private final String message;
    private final Object data;

    public ForbiddenException(String message, Object data) {
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
