package com.skybooking.skyhistoryservice;

import com.skybooking.skyhistoryservice.v1_0_0.util.calculator.Calculator;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightShoppingBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.Localization;
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
    Calculator calculator() {
        return new Calculator();
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
    FlightShoppingBean flightShopping() {
        return new FlightShoppingBean();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
