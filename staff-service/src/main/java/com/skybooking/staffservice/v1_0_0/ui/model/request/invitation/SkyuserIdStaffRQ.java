package com.skybooking.staffservice.v1_0_0.ui.model.request.invitation;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SkyuserIdStaffRQ {

    @NotNull(message = "Please provide a user id")
    private Long userId;

    @NotNull(message = "Please provide a role")
    @NotEmpty(message = "Please provide a role")
    private String skyuserRole;

}
