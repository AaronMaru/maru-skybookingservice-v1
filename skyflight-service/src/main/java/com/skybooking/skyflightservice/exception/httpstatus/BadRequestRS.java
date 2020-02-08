package com.skybooking.skyflightservice.exception.httpstatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BadRequestRS extends CustomErrorResponse {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BadRequestRS(String message, Object error) {
        this.setTimestamp(dateFormat.format(new Date()));
        this.setStatus(400);
        this.setMessage(message);
        this.setError(error);
    }
}
