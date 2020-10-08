package com.skybooking.skypointservice;

import com.skybooking.skypointservice.util.HeaderDataUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkypointServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkypointServiceApplication.class, args);
    }

    @Bean
    HeaderDataUtil headerDataUtil() {
        return new HeaderDataUtil();
    }

}
