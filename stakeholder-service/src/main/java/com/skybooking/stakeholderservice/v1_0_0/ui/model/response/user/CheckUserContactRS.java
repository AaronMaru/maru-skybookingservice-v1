package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user;

import lombok.Data;

@Data
public class CheckUserContactRS {
    private String phoneCode;
    private String phoneNumber;
    private String email;
    private Boolean isPassword;
}
