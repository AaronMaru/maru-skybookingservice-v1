package com.skybooking.paymentservice;

import com.skybooking.paymentservice.v1_0_0.util.header.HeaderBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class PaymentServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PaymentServiceApplication.class, args);

    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }
}
