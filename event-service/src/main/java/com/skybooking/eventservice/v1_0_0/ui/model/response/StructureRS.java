package com.skybooking.eventservice.v1_0_0.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
public class StructureRS {

    private int status = HttpStatus.OK.value();
    private String message;
    private Object data;

    public StructureRS() {}

    public StructureRS(String message) {
        this.message = message;
    }
}