package com.skybooking.stakeholderservice.exception.httpstatus;

public class ForbiddenException extends RuntimeException {

    private String message;
    private Object data;

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
