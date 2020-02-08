package com.skybooking.skyflightservice.exception.httpstatus;

public class NotFoundException extends ExceptionResponse {

    public NotFoundException(String message, Object error) {
        super(message, error);
    }
}
