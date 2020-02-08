package com.skybooking.stakeholderservice.login;

import com.skybooking.stakeholderservice.v1_0_0.util.tdd.GeneralTdd;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTests {


    @Test
    public void login() {

        GeneralTdd login = new GeneralTdd();

        login.loginFunc("ninin@skybooking.info", "Admin@123", null);
    }

    //Unauthorized 401
    @Test
    public void loginWrongAcc() {
        GeneralTdd login = new GeneralTdd();
        login.loginFunc("ninin168@skybooking.info", "Admin@1231", null);
    }

    //Unauthorized 401
    @Test
    public void loginWrongBasicAuth() {
        GeneralTdd login = new GeneralTdd();
        login.loginFunc("ninin@skybooking.info", "Admin@123", "skyflight");
    }

    //Bad Request 400
    @Test
    public void loginNoData() {
        GeneralTdd login = new GeneralTdd();
        login.loginFunc("", "", null);
    }

}
