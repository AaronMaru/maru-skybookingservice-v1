package com.skybooking.staffservice;

import com.skybooking.staffservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.staffservice.v1_0_0.util.email.EmailBean;
import com.skybooking.staffservice.v1_0_0.util.general.ApiBean;
import com.skybooking.staffservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.staffservice.v1_0_0.util.localization.LocalizationBean;
import com.skybooking.staffservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties
public class StaffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }

    @Bean
    LocalizationBean localization() {
        return new LocalizationBean();
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
    EmailBean email() {
        return new EmailBean();
    }

    @Bean
    DateTimeBean dateTimeBean() {
        return new DateTimeBean();
    }

    @Bean
    PushNotificationOptions notificationOptions() {
        return new PushNotificationOptions();
    }

}
