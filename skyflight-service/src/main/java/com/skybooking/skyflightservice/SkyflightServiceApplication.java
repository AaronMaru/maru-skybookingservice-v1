package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyflightservice.v1_0_0.util.email.EmailBean;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @Bean
    DateTimeBean dateTimeBean() {
        return new DateTimeBean();
    }

    @Bean
    EmailBean email() {
        return new EmailBean();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
