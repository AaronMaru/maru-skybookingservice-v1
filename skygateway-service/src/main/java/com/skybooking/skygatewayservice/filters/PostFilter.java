package com.skybooking.skygatewayservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.skybooking.skygatewayservice.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;

public class PostFilter extends ZuulFilter {

    @Autowired private TranslationService translationService;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        System.out.println("Inside Post Filter");
        translationService.message();

        return null;
    }
}