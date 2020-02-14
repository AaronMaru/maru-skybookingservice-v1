package com.skybooking.paymentservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
public class AppConfig {

    @Value("${spring.service.flight.url}")
    private String flightUrl;

    @Value("${spring.service.flight.version}")
    private String flightVersion;

    @Value("${spring.website.payment.fail}")
    private String paymentFailPage;

    @Value("${spring.website.ticket.succeed}")
    private String ticketSucceedPage;

    @Value("${spring.website.ticket.fail}")
    private String ticketFailPage;

    @Value("${spring.PIPAY.VERIFY_TRANSACTION}")
    private String pipayVerification;

}
