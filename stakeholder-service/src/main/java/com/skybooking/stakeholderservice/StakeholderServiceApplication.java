package com.skybooking.stakeholderservice;

import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.AwsPartBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.NotificationBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties
public class StakeholderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StakeholderServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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
    LocalizationBean localization() {
        return new LocalizationBean();
    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }

    @Bean
    SkyownerBean skyownerBean() {
        return new SkyownerBean();
    }

    @Bean
    NotificationBean notificationBean() {
        return new NotificationBean();
    }

    @Bean
    EmailBean emailBean() {
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

    @Bean
    AwsPartBean awsPartBean() {
        return new AwsPartBean();
    }
}
