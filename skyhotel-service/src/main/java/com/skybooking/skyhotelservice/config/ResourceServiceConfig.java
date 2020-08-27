package com.skybooking.skyhotelservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
            .antMatchers(
                    "/v1.0.0/destination/**",
                    "/v1.0.0/popular-city/**",
                    "/v1.0.0/recommend-hotel/**",
                    "/v1.0.0/availability/**",
                    "/v1.0.0/similar-hotel/**",
                    "/v1.0.0/payment/**"
            ).permitAll()
            .antMatchers("/**").authenticated();
    }

}