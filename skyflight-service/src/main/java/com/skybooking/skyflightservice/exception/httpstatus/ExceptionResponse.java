package com.skybooking.skyflightservice.exception.httpstatus;

public class ExceptionResponse extends RuntimeException {

    private String message;
    private Object error;

    public ExceptionResponse(String message, Object error) {
        this.message = message;
        this.error = error;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Object getError() {
        return error;
    }
}
