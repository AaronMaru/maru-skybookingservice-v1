package com.skybooking.stakeholderservice.verify;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.verify.VerifyRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.tdd.GeneralTdd;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VerifyTest {

    @Test
    public void loginVerify() {
        GeneralTdd s = new GeneralTdd();
        s.loginFunc("ninin@skybooking.info", "Admin@123", null);

    }



    public void verify() {
        RestTemplate restAPi = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        var rq = new VerifyRQ();

        rq.setToken("869272");

        HttpEntity<Object> requestSMS = new HttpEntity(rq, headers);

        try {
            restAPi.exchange("http://127.0.0.1:7004/wv1.0.0/auth/verify?_method=patch", HttpMethod.POST, requestSMS, Object.class);
        } catch (Exception e) {
            throw e;
        }

    }

}
