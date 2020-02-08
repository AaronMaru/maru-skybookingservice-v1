package com.skybooking.skyflightservice.exception.httpstatus;

public class GoneException extends ExceptionResponse {

    public GoneException(String message, Object error) {
        super(message, error);
    }
}
