package com.skybooking.skygatewayservice.service;

import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

public class BaseService {

    private static final String UNAUTHORIZED = "{\"status\": 401,\"message\": \"UNAUTHORIZED\",\"error\": null}";
    private static final String FORBIDDEN = "{\"status\": 403,\"message\": \"FORBIDDEN\",\"error\": null}";
    private static final String UNPROCESSABLE_ENTITY = "{\"status\": 422,\"message\": \"UNPROCESSABLE_ENTITY\",\"error\": null}";

    protected void response(HttpStatus status, String responseBody) {
        RequestContext ctx = getCurrentContext();
        ctx.setResponseStatusCode(status.value());

        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(responseBody);
            ctx.setSendZuulResponse(false);
        }
    }

    protected void responseUnauthorized() {
        response(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
    }

    protected void responseForbidden() {
        response(HttpStatus.FORBIDDEN, FORBIDDEN);
    }

    protected void responseUnProcess() {
        response(HttpStatus.UNPROCESSABLE_ENTITY, UNPROCESSABLE_ENTITY);
    }
}
