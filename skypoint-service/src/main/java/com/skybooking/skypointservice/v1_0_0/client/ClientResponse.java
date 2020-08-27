package com.skybooking.skypointservice.v1_0_0.client;

import lombok.Data;

import java.util.Map;

@Data
public class ClientResponse {
    private String message;
    private Integer status;
    private Map<String, Object> data;
}
