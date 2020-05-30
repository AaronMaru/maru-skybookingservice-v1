package com.skybooking.skygatewayservice.service;

import com.skybooking.skygatewayservice.constant.AuthConstant;
import com.skybooking.skygatewayservice.io.repository.OauthUserRP;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private OauthUserRP oauthUserRP;

    public int checkToken(String authorization) {

        if (authorization != null && authorization.contains("Bearer")){

            String tokenId = this.getClaim(authorization.substring("Bearer".length()+1), "jti", String.class);

            var auth = oauthUserRP.getFirst(tokenId);
            System.out.println(auth);
            if (auth == null) {
                return AuthConstant.REVOKED;
            }

            return auth.getStatus();
        }

        return AuthConstant.REVOKED;
    }

    public <T extends Object> T getClaim(String accessToken, String claimKey, Class<T> type) {

        try {

            JsonParser parser = JsonParserFactory.getJsonParser();
            Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(accessToken).getClaims());

            return (T) ConvertUtils.convert(tokenData.get(claimKey), type);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
