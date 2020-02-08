package com.skybooking.skyflightservice.exception.httpstatus;

import lombok.Data;

@Data
public class CustomErrorResponse {

    private String timestamp;
    private int status;
    private String message;
    private Object error;

}

