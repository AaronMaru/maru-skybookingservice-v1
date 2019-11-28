package com.skybooking.skygatewayservice.provider.fallback;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.skybooking.skygatewayservice.provider.ApiFallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

public class StakeholderFB extends ApiFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "stakeholder";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, final Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else if (cause instanceof InternalError) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return response(HttpStatus.OK);
        }
    }
}
