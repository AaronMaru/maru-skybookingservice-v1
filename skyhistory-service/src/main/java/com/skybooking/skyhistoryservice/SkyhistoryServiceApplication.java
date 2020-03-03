package com.skybooking.skyhistoryservice;

import com.skybooking.skyhistoryservice.v1_0_0.util.calculator.CalculatorBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.email.EmailBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightShoppingBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.notification.NotificationBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SkyhistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyhistoryServiceApplication.class, args);
    }

    @Bean
    CalculatorBean calculator() {
        return new CalculatorBean();
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
    FlightShoppingBean flightShopping() {
        return new FlightShoppingBean();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    NotificationBean notificationBean() { return new NotificationBean(); }

    @Bean
    EmailBean email() {
        return new EmailBean();
    }

    @Bean
    DateTimeBean dateTimeBean() {
        return new DateTimeBean();
    }

    @Bean
    ApiBean apiBean() { return new ApiBean(); }

}
