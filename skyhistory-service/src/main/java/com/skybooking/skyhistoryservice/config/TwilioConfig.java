package com.skybooking.skyhistoryservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties(prefix = "spring.twilio")
@Getter
@Setter
@Validated
public class TwilioConfig {

    @NotEmpty
    private String accountSID;

    @NotEmpty
    private String authToken;

    @NotEmpty
    private String phoneNumber;


}
