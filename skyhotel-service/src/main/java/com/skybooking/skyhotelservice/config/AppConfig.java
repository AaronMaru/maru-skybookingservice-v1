package com.skybooking.skyhotelservice.config;

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

    @Value("${hazelcast.expire}")
    private Integer HAZELCAST_EXPIRED_TIME;
    
    @Value("${spring.service.hotel-ds.url}")
    private String hotelUrl;

    @Value("${spring.service.event.url}")
    private String eventService;

    @Value("${spring.service.hotel-ds.version}")
    private String hotelVersion;

    @Value("${spring.awsImageUrl.popularcity.medium}")
    private String POPULAR_CITY_IMG_URL;


}
