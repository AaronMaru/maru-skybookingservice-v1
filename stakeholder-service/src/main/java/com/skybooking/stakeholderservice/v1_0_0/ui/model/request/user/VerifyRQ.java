package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import javax.validation.constraints.NotEmpty;

public class VerifyRQ {

    @NotEmpty(message = "Please provide login code")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
