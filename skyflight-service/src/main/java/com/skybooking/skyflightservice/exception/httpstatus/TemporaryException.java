package com.skybooking.skyflightservice.exception.httpstatus;

public class TemporaryException extends ExceptionResponse {

    public TemporaryException(String message, Object error) {
        super(message, error);
    }
}
