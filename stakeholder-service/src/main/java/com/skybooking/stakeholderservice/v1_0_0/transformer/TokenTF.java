package com.skybooking.stakeholderservice.v1_0_0.transformer;

import lombok.Data;

@Data
public class TokenTF {

    private Long userId;
    private String access_token;
    private String refresh_token;

}
