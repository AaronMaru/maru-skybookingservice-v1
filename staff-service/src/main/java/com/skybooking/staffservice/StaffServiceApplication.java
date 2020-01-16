package com.skybooking.staffservice;

import com.skybooking.staffservice.v1_0_0.util.general.ApiBean;
import com.skybooking.staffservice.v1_0_0.util.general.Duplicate;
import com.skybooking.staffservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.staffservice.v1_0_0.util.localization.Localization;
import com.skybooking.staffservice.v1_0_0.util.notification.NotificationBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StaffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffServiceApplication.class, args);
    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }

    @Bean
    Localization localization() {
        return new Localization();
    }

    @Bean
    ApiBean apiBean() {
        return new ApiBean();
    }

    @Bean
    GeneralBean generalBean() {
        return new GeneralBean();
    }

    @Bean
    NotificationBean notificationBean() { return new NotificationBean(); }

    @Bean
    Duplicate duplicate() {
        return new Duplicate();
    }

}
