package com.skybooking.skyflightservice.exception.httpstatus;

public class ForbiddenException extends ExceptionResponse {

    public ForbiddenException(String message, Object error) {
        super(message, error);
    }
}
