package com.skybooking.paymentservice.v1_0_0.util.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthUtility {

    @Autowired
    private HttpServletRequest request;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get authentication token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     */
    public String getAuthToken() {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

}
