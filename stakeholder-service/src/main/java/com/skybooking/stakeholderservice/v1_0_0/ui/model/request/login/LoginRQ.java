package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.skybooking.stakeholderservice.exception.anotation.Code;
import com.skybooking.stakeholderservice.exception.anotation.Email;
import com.skybooking.stakeholderservice.exception.anotation.Phone;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Code(first = "username", second = "code", message = "Please provide country code")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginRQ {

    @NotNull(message = "Please provide a username")
    @NotEmpty(message = "Please provide a username")
    @Phone
    @Email
    private String username;

    @NotNull(message = "Please provide a password")
    @NotEmpty(message = "Please provide a password")
    private String password;

    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
