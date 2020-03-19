package com.skybooking.paymentservice.v1_0_0.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.security.jwt.JwtHelper;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class JwtUtils {

    @Autowired
    HttpServletRequest request;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * jwt decoding to get claim value from authentication
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param claimKey
     * @return Object
     */
    public <T extends Object> T getClaim(String claimKey, Class<T> type) {

        try {

            JsonParser parser = JsonParserFactory.getJsonParser();
            String authorization = request.getHeader("Authorization");
            Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(authorization.split(" ")[1]).getClaims());

            return (T) ConvertUtils.convert(tokenData.get(claimKey), type);

        } catch (Exception ex) {
            return null;
        }
    }
}
