package com.skybooking.skyflightservice.exception.httpstatus;

public class UnauthorizedException extends ExceptionResponse {

    public UnauthorizedException(String message, Object error) {
        super(message, error);
    }
}
