package com.skybooking.stakeholderservice.v1_0_0.service;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.base.BaseResponse;
import org.springframework.http.HttpStatus;

public class BaseService {

    protected BaseResponse responseBodyWithSuccessMessage()
    {
        return responseBody(HttpStatus.OK, "", null);
    }

    protected BaseResponse responseBodyWithSuccessMessage(Object data)
    {
        return responseBody(HttpStatus.OK, "", data);
    }

    protected BaseResponse responseBody(HttpStatus status)
    {
        return responseBody(status, null, null);
    }

    protected BaseResponse responseBody(HttpStatus status, String message)
    {
        return responseBody(status, message, null);
    }

    protected BaseResponse responseBody(HttpStatus status, String message, Object data)
    {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(message);
        baseResponse.setData(data);

        return baseResponse;
    }
}
