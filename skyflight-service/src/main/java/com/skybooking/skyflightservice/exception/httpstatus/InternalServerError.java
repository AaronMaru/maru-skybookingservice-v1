package com.skybooking.skyflightservice.exception.httpstatus;

public class InternalServerError extends ExceptionResponse {

    public InternalServerError(String message, Object error) {
        super(message, error);
    }
}
