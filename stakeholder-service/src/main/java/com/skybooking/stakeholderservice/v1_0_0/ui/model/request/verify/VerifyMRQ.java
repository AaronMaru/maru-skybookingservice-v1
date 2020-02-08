package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify;

import javax.validation.constraints.NotEmpty;

public class VerifyMRQ {

    @NotEmpty(message = "Please provide login code")
    private String token;
    private String username;
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
