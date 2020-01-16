package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import javax.validation.constraints.NotEmpty;

public class DeactiveAccountRQ {

    @NotEmpty(message = "Please provide current password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
