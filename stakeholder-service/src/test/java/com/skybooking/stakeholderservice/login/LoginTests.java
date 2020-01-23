package com.skybooking.stakeholderservice.login;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login.LoginRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTests {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    Environment environment;

    @Test
    public void login() {
        loginFunc("ninin@skybooking.info", "Admin@123", null);
    }

    //Unauthorized 401
    @Test
    public void loginWrongAcc() {
        loginFunc("ninin168@skybooking.info", "Admin@1231", null);
    }

    //Unauthorized 401
    @Test
    public void loginWrongBasicAuth() {
        loginFunc("ninin@skybooking.info", "Admin@123", "skyflight");
    }

    //Bad Request 400
    @Test
    public void loginNodata() {
        loginFunc("", "", null);
    }


    public void loginFunc(String username, String password, String auth) {

        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("X-PlayerId", "312312AWV");
        headers.setBasicAuth(auth != null ? auth :environment.getProperty("spring.oauth2.client-id"), environment.getProperty("spring.oauth2.client-secret"));

        LoginRQ rq = new LoginRQ();
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
