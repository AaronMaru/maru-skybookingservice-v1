package com.skybooking.skyflightservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Getter
@Setter
public class AppConfig {

    @Value("${spring.application.name}")
    private String APP_ID;

    @Value("${spring.service.distributed.uri}")
    private String DISTRIBUTED_URI;

    @Value("${spring.service.distributed.user}")
    private String DISTRIBUTED_USER;

    @Value("${spring.service.distributed.secret}")
    private String DISTRIBUTED_SECRET;

    @Value("${spring.service.distributed.version}")
    private String DISTRIBUTED_VERSION;

    @Value("${spring.service.stakeholder.uri}")
    private String STAKEHOLDER_URI;

    @Value("${spring.service.stakeholder.version}")
    private String STAKEHOLDER_VERSION;

    @Value("${spring.service.stakeholder.common-version}")
    private String STAKEHOLDER_COMMON_VERSION;

    @Value("${spring.service.stakeholder.path}")
    private String STAKEHOLDER_PATH;

    @Value("${spring.aws.storage.logo.airline}")
    private String AIRLINE_LOGO_PATH;

    @Value("${hazelcast.expire}")
    private Integer HAZELCAST_EXPIRED_TIME;

    @Value("${spring.service.skyhistory.uri}")
    private String SKYHISTORY_URI;

    @Value("${spring.service.skyhistory.version}")
    private String SKYHISTORY_VERSION;

}
