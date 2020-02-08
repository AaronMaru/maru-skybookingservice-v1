package com.skybooking.stakeholderservice.v1_0_0.util.tdd;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class GeneralTdd {

    @Autowired
    Environment environment;


    public void loginFunc(String username, String password, String auth) {

        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("X-PlayerId", "312312AWV");
        headers.setBasicAuth(auth != null ? auth : "skybooking", "168@skybooking");

        var rq = new LoginRQ();
        rq.setPassword(password);
        rq.setUsername(username);

        HttpEntity<Object> requestSMS = new HttpEntity(rq, headers);

        try {
            restAPi.postForObject("http://127.0.0.1:7004/wv1.0.0/auth/login", requestSMS, UserDetailsTokenRS.class);
        } catch (Exception e) {
            throw e;
        }

    }

}
