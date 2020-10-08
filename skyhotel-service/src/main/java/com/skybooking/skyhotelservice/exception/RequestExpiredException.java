package com.skybooking.skyhotelservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestExpiredException extends RuntimeException {

    private Date timestamp;
    private Integer status;
    private Object data;
    private String requestId;

    public RequestExpiredException(String requestId) {
        super("The previous request has been expired.");
        this.timestamp = new Date();
        this.status = HttpStatus.BAD_REQUEST.value();
    }

}
