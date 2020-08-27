package com.skybooking.skypointservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
public class AppConfig {

    @Value("${spring.service.payment.url}")
    private String paymentUrl;

    @Value("${spring.service.payment.version}")
    private String paymentVersion;

    @Value("${spring.service.payment.new-version}")
    private String paymentNewVersion;

    @Value("${spring.service.stakeholder.url}")
    private String stakeholderUrl;

    @Value("${spring.service.stakeholder.version}")
    private String stakeholderVersion;

    @Value("${spring.service.event.url}")
    private String eventUrl;

    @Value("${spring.service.event.version}")
    private String eventVersion;
}
