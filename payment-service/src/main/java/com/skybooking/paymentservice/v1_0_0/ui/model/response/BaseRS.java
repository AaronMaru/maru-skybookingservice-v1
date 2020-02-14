package com.skybooking.paymentservice.v1_0_0.ui.model.response;

import lombok.Data;

@Data
public class BaseRS<T> {

    private Integer status;
    private String message;
    private T data;

    public BaseRS(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
