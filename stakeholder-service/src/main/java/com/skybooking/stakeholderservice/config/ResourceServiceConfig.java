package com.skybooking.stakeholderservice.config;

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
            .antMatchers("/**v1.0.0/auth/**",
                "/**v1.1.0/auth/**",
                "/**v1.0.0/utils/**",
                "/oauth/check_token",
                "/oauth/token/**",
                "/v1.0.0/back-office/oauth/token/**")
            .permitAll()
                
            .antMatchers("/**").authenticated();
    }

}
