package com.skybooking.skyhistoryservice.v1_0_0.util.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class AuthUtil {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

    public UserAuth user() throws AuthenticationException, InvalidTokenException {

        String authorization = request.getHeader("Authorization");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", authorization);
        UserAuth user = executePost("http://192.168.2.3:7000/stakeholder/wv1.0.0/user", requestHeaders);
        if (user == null) {
            throw new InvalidTokenException("Token not allowed");
        }

        return user;
    }

    private UserAuth executePost(String path, HttpHeaders headers) {
        try {
            if (headers.getContentType() == null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            Map map = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<MultiValueMap<String, String>>(null, headers), Map.class).getBody();
            UserAuth user = new UserAuth(map);
            return user;
        } catch (Exception ex) {
            return null;
        }
    }
}