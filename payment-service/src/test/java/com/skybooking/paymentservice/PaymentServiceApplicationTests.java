package com.skybooking.paymentservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceApplicationTests {

    @Test
    public void contextLoads() {

        Map<String, Object> map = new HashMap<>();
        map.put("amount", 10.0);

        BigDecimal test = new BigDecimal(map.get("amount").toString());

        System.out.println("abc" + test);
    }

}
