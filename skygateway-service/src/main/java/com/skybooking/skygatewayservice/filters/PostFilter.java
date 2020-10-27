package com.skybooking.skygatewayservice.filters;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.skybooking.skygatewayservice.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostFilter extends ZuulFilter {

    @Autowired
    private TranslationService translationService;

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

        RequestContext context = RequestContext.getCurrentContext();
        List<Pair<String, String>> responseHeaders = context.getOriginResponseHeaders();

        Boolean isHTML = false;

        for (Pair<String, String> item : responseHeaders) {
            if (item.second().equals("text/html;charset=UTF-8")) {
                isHTML = true;
                break;
            }
        }

        if (!isHTML) {
            translationService.message();
        }

        return null;
    }
}