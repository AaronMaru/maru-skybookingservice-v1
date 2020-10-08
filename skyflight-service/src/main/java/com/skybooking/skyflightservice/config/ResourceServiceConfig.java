package com.skybooking.skyflightservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
            .antMatchers(
                "/v1.0.0/shopping/flight/test-revalidate",
                "/v1.0.0/shopping/flight/detail",
                "/v1.0.0/shopping/search/**",
                "/v1.0.0/flight/sharing/**",
                "/v1.0.0/sb-**",
                "/v1.0.0/sb-**/**",
                "/v1.0.0/payment/**",
                "/v1.0.0/ticketing/**"
            ).permitAll()
            .antMatchers(
                "/v1.0.0/booking/**",
                "/v1.0.0/bookmark/**",
                "/**"
            ).authenticated();
    }

}