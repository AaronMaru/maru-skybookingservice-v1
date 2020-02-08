package com.skybooking.skyflightservice.exception.httpstatus;

public class BadRequestException extends ExceptionResponse {

    public BadRequestException(String message, Object error) {
        super(message, error);
    }
}
