package com.skybooking.skygatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class GatewayConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(
                "/stakeholder/**v1.0.0/auth/**",
                "/stakeholder/**v1.0.0/utils/**",
                "/staff/**v1.0.0/auth/**",
                "/staff/**v1.0.0/utils/**",
                "/flight/v1.0.0/shopping/flight/test-revalidate",
                "/flight/v1.0.0/shopping/flight/detail",
                "/flight/v1.0.0/shopping/search/**",
                "/flight/v1.0.0/sb-**",
                "/flight/v1.0.0/sb-**/**",
                "/flight/v1.0.0/payment/**",
                "/flight/v1.0.0/ticketing/**",
                "/flight/v1.0.0/flight/sharing/**",
                "/payment/ipay88/response/**",
                "/payment/pipay/success/**",
                "/payment/pipay/fail/**",
                "/payment/pipay/form/**",
                "/payment/ipay88/form/**",
                "/skyhistory/wv1.0.0/payment-success/no-auth/**",
                "/skyhistory/wv1.0.0/receipt-itinerary/**",
                "/hotel/v1.0.0/destination/**",
                "/hotel/v1.0.0/popular-city/**",
                "/hotel/v1.0.0/recommend-hotel/**",
                "/hotel/v1.0.0/availability/**",
                "/hotel/v1.0.0/similar-hotel/**",
                "/back-office/v1.0.0/**",
                "/back-office/v1.0.0/**/**",
                "/back-office/v1.0.0/**/**/**",
                "/back-office-point/v1.0.0/**",
                "/back-office-point/v1.0.0/**/**",
                "/back-office-point/v1.0.0/**/**/**",
                "/event/v1.0.0/email/no-auth/**",
                "/event/v1.0.0/notification/no-auth/**",
                "/skypoint/v1.0.0/top-up/online/post/**",
                "/stakeholder/v1.0.0/back-office/oauth/token/**"
            ).permitAll()
            .antMatchers(
                "/flight/v1.0.0/booking/**/**",
                "/stakeholder/wv1.0.0/utils/settings/notifications",
                "/flight/v1.0.0/bookmark/**",
                "/payment/v1.0.0/request/**",
                "/payment/v1.0.0/methods/**",
                "/**"
            ).authenticated();
    }
}