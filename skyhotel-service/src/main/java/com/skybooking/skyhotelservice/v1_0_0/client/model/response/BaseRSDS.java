package com.skybooking.skyhotelservice.v1_0_0.client.model.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseRSDS<T> {

    private int status;
    private String message = "";
    private List<T> data;

}
