package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.stakeholderservice.exception.anotation.*;

import javax.validation.constraints.NotEmpty;

@Code(first = "username", second = "code", message = "Please provide country code")
@UsernameUnique(first = "username", second = "code", message = "Username alraedy exists")
public class UpdateContactRQ {

    @Phone
    @Email
    @NotEmpty(message = "Please provide a email or phone number")
    private String username;

    private String password;

    private String code;

    @Token(status = 2)
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
