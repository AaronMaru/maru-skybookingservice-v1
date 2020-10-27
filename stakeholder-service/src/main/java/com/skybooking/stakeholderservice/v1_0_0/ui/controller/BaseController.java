package com.skybooking.stakeholderservice.v1_0_0.ui.controller;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity<BaseResponse> response(BaseResponse response)
    {
        return ResponseEntity
            .status(response.getStatus())
            .body(response);
    }
}
