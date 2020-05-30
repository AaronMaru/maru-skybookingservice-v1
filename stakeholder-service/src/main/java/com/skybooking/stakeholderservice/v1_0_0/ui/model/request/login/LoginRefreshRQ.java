package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import com.skybooking.stakeholderservice.exception.anotation.Code;

import javax.validation.constraints.NotNull;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginRefreshRQ {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
