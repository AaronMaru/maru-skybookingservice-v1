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

    @Value("${spring.website.payment}")
    private String paymentPage;

    @Value("${spring.PIPAY.VERIFY_TRANSACTION}")
    private String pipayVerification;

    @Value("${spring.imagePath.payment}")
    private String imagePathPaymentMethod;

    @Value("${spring.service.stakeholder.url}")
    private String stakeholder;

    @Value("${spring.service.stakeholder.version}")
    private String stakeholderVersion;

    @Value("${spring.serverAddress}")
    private String paymentUrl;
}
