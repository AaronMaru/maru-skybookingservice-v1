package com.skybooking.paymentservice.config;

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
                        "/ipay88/response/**",
                        "/pipay/success/**",
                        "/pipay/fail/**",
                        "/pipay/form/**",
                        "/ipay88/form/**"
                ).permitAll()
                .antMatchers(
                        "/v1.0.0/request/**",
                        "/v1.0.0/methods/**"
                ).authenticated();
    }

}