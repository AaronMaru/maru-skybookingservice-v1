package com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BasicAuthRQ {

    @NotBlank(message = "username is required.")
    private String username;

    @NotBlank(message = "password is required.")
    private String password;

    @NotBlank(message = "grant type is required.")
    private String grantType;

}
