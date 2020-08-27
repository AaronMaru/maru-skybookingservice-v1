package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.backoffice.authentication;

import lombok.Data;

@Data
public class TokenRS {

    private String accessToken;
    private String tokenType;
    private Integer expiredIn;
    private String refreshToken;
}
