package com.skybooking.stakeholderservice;

import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StakeholderServiceApplicationTests {

    @Autowired
    private UserBean userBean;

    @Autowired
    private GeneralBean generalBean;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {

    }

}
