package com.skybooking.skyflightservice.exception.httpstatus;

public class PermanentException extends ExceptionResponse {

    public PermanentException(String message, Object error) {
        super(message, error);
    }
}

