package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

public class SendVerifyRQ {

    private String username;
    private String code;

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
