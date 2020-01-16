package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkyflightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyflightServiceApplication.class, args);
    }

    @Bean
    Localization localization() {
        return new Localization();
    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }

}
