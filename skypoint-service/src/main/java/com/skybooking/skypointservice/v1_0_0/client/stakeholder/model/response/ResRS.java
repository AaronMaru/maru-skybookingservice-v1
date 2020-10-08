package com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResRS {

    private int status;
    private String message;
    private Object data;

}
