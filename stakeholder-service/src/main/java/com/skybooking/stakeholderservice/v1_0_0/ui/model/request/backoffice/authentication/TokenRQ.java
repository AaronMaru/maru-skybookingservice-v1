package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.backoffice.authentication;

import com.skybooking.stakeholderservice.exception.anotation.Include;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenRQ {

    @NotNull(message = "Grant type is required")
    @Include(contains = "client_credentials|refresh_token", delimiter = "\\|", message = "Grant type is invalid")
    private String grantType;
}
