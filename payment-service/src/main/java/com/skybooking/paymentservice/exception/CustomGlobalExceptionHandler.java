package com.skybooking.paymentservice.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private CustomErrorAttributes customErrorAttributes;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        var body = customErrorAttributes.getErrorAttributes(request, false);

        String message = "";
        body.put("status", status.value());

        if (ex.getBindingResult().getAllErrors().size() > 0) {
            message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (ex.getBindingResult().getFieldErrors().size() > 0) {
            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        body.put("message", message);

        Map<String, Object> error = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            error.put(e.getField(), e.getDefaultMessage());
        });
        body.put("error", error);

        return new ResponseEntity<>(body, headers, status);

    }

}
