package com.skybooking.skyflightservice.v1_0_0.util;

import java.util.Base64;

public class GeneratorUtility {

    public String getEncodeBase64(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

}
