package com.skybooking.staffservice.v1_0_0.ui.model.response.invitation;

import lombok.Data;

@Data
public class PendingEmailStaffRS {

    private Integer id;
    private String username;
    private String role;
    private String invitedAt;
    private Boolean isAvailable;

}
