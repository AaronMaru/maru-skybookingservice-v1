package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import com.skybooking.stakeholderservice.exception.anotation.Code;

import javax.validation.constraints.NotNull;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

@Code(first = "username", second = "code", message = "Please provide country code")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginRQ {

    @NotNull(message = "Please provide a username")
    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
    private String username;

    @NotNull(message = "Please provide a password")
    @IsNotEmpty
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
