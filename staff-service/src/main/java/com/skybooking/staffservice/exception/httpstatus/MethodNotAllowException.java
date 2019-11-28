package com.skybooking.staffservice.exception.httpstatus;

public class MethodNotAllowException extends RuntimeException {

    private String message;
    private Object data;

    public MethodNotAllowException(String message, Object data) {
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
