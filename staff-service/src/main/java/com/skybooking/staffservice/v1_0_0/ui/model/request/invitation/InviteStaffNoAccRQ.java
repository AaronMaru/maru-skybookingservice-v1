package com.skybooking.staffservice.v1_0_0.ui.model.request.invitation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InviteStaffNoAccRQ {

    @NotEmpty(message = "Please provide a username")
    @NotNull(message = "Please provide a username")
    private String username;

    @NotEmpty(message = "Please provide a skyuser role")
    @NotNull(message = "Please provide a skyuser role")
    private String skyuserRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }
}
