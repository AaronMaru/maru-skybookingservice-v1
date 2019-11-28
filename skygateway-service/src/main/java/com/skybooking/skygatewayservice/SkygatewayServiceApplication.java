package com.skybooking.skygatewayservice;

import com.skybooking.skygatewayservice.filters.ErrorFilter;
import com.skybooking.skygatewayservice.filters.PostFilter;
import com.skybooking.skygatewayservice.filters.PreFilter;
import com.skybooking.skygatewayservice.filters.RouteFilter;
import com.skybooking.skygatewayservice.provider.fallback.PaymentFB;
import com.skybooking.skygatewayservice.provider.fallback.SkyhistoryFB;
import com.skybooking.skygatewayservice.provider.fallback.StaffFB;
import com.skybooking.skygatewayservice.provider.fallback.StakeholderFB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableEurekaClient
@Configuration
@EnableZuulProxy
public class SkygatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkygatewayServiceApplication.class, args);
    }

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public LocationRewriteFilter locationRewriteFilter() {
        return new LocationRewriteFilter();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("Authorization", "Content-Type", "Accept")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
            }
        };
    }

    @Bean
    public PaymentFB paymentFB() {
        return new PaymentFB();
    }

    @Bean
    public SkyhistoryFB skyhistoryFB() {
        return new SkyhistoryFB();
    }

    @Bean
    public StaffFB staffFB() {
        return new StaffFB();
    }

    @Bean
    public StakeholderFB stakeholderFB() {
        return new StakeholderFB();
    }

}
