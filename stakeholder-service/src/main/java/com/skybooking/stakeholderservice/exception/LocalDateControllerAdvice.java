package com.skybooking.stakeholderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class LocalDateControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<Object> handleLocaleDateFormatException(DateTimeParseException ex) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("data", null);
        body.put("message", "The input date is invalid.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }
}
