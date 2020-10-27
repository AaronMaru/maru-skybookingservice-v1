package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base;

import lombok.Data;

@Data
public class BaseResponse {
    private int status;
    private String message;
    private Object data;
}
