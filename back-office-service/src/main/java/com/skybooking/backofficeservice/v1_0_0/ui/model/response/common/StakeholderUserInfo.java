package com.skybooking.backofficeservice.v1_0_0.ui.model.response.common;

import lombok.Data;

@Data
public class StakeholderUserInfo {
    private String firstName;
    private String lastName;
    private String userCode;
    private String phone;
    private String phoneCode;
    private String email;
}
