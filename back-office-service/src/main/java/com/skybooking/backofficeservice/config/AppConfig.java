package com.skybooking.backofficeservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Data
public class AppConfig {

    @Value("${spring.application.name}")
    private String APP_ID;

    @Value("${spring.service.distributed.url}")
    private String DISTRIBUTED_URL;

    @Value("${spring.service.distributed.auth.clientId}")
    private String AUTH_CLIENT_ID;

    @Value("${spring.service.distributed.auth.clientSecret}")
    private String AUTH_CLIENT_SECRET;

    @Value("${spring.service.sky.url}")
    private String SKY_URL;

}
