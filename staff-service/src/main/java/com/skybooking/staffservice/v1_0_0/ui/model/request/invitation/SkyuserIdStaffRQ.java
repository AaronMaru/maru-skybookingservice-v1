package com.skybooking.staffservice.v1_0_0.ui.model.request.invitation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SkyuserIdStaffRQ {

    private Long userId;

    @NotNull(message = "Please provide a role")
    @NotEmpty(message = "Please provide a role")
    private String skyuserRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }

}
