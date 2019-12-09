package com.skybooking.skyflightservice.v1_0_0.ui.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasicAuthRS {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("jti")
    private String jti;

    @JsonProperty("expires_in")
    private int expiresIn;

}
