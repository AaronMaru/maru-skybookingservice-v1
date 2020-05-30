package com.skybooking.stakeholderservice.exception.httpstatus;

public class ConflictException extends RuntimeException {

    private String message;
    private Object data;

    public ConflictException (String message, Object data) {
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
