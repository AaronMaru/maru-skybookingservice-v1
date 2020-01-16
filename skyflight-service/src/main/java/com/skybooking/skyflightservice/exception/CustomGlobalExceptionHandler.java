package com.skybooking.skyflightservice.exception;

import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Localization localization;
    // Error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        String validation = "";

        body.put("data", "");
        for (FieldError fieldError: fieldErrors) {
            validation = localization.multiLanguageRes(fieldError.getDefaultMessage());
            if (fieldError.getCode().equals("UsernameUnique")) {
                if (result.getFieldValue("typeSky").equals("bussiness")) {
                    HashMap<String, Object> skyowner = new HashMap<>();
                    skyowner.put("email", result.getFieldValue("username"));
                    skyowner.put("typeSky", result.getFieldValue("typeSky"));
                    body.put("data", skyowner);
                }
            }
        }
        body.put("message", validation);

        return new ResponseEntity<>(body, headers, status);

    }
}
