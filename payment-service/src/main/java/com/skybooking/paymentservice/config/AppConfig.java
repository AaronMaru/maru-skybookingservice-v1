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

    @Value("${spring.service.hotel.url}")
    private String hotelUrl;

    @Value("${spring.service.hotel.version}")
    private String hotelVersion;

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

    @Value("${spring.service.point.url}")
    private String pointUrl;

    @Value("${spring.service.point.version}")
    private String pointVersion;
}
