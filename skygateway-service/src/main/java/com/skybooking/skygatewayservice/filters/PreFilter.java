package com.skybooking.skygatewayservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.skybooking.skygatewayservice.constant.AuthConstant;
import com.skybooking.skygatewayservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {

    @Autowired
    private AuthService authService;

    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public String filterType() {
        return "pre";
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

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

        var authorization = request.getHeader("Authorization");


        /**
         * Check each of endpoint that they need user access token
         */
        if (authorization != null && authorization.contains("Bearer")) {

            var auth = authService.checkToken(authorization);

            if (auth == AuthConstant.REVOKED) { // user logged out
                this.setFailedRequest(HttpStatus.UNAUTHORIZED, "{\"status\": 401,\"message\": \"Unauthorized\",\"error\": null}");
            }

            if (auth == AuthConstant.PERMISSION_CHANGED) { // user permission have changed by someone
                this.setFailedRequest(HttpStatus.FORBIDDEN, "{\"status\": 403,\"message\": \"Forbidden\",\"error\": null}");
            }

            this.validationCompanyId(request, authorization);

        }

        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reports an error message given a response body and code.
     * -----------------------------------------------------------------------------------------------------------------
     * @param status
     * @param body
     */
    private void setFailedRequest(HttpStatus status, String body) {

        log.debug("Reporting error ({}): {}", status.value(), body);

        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(status.value());

        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Validation company id
     * -----------------------------------------------------------------------------------------------------------------
     * @param request
     * @param token
     */
    private void validationCompanyId(HttpServletRequest request, String token) {

        Long companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty())
                ? Long.valueOf(request.getHeader("X-CompanyId"))
                : 0;

        if (companyId != 0) {
            String tokenId = token.substring("Bearer".length()+1);

            Long tokenCompanyId = authService.getClaim(tokenId,"companyId", Long.class);

            if (!companyId.equals(tokenCompanyId)) {
                this.setFailedRequest(HttpStatus.FORBIDDEN, "{\"status\": 403,\"message\": \"Forbidden\",\"error\": null}");
            }
        }

    }

}
