package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user;

import lombok.Data;

import javax.persistence.Column;


@Data
public class TokenRS {

    private Long userId;
    private String token;
    private String refreshToken;

}
