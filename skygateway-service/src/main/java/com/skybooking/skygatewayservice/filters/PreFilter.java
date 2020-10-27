package com.skybooking.skygatewayservice.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.skybooking.skygatewayservice.constant.AuthConstant;
import com.skybooking.skygatewayservice.service.AuthService;
import com.skybooking.skygatewayservice.service.EncryptionDecryptionService;
import com.skybooking.skygatewayservice.service.StakeholderService;
import com.skybooking.skygatewayservice.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

public class PreFilter extends ZuulFilter {

    @Autowired
    private StakeholderService stakeholderService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EncryptionDecryptionService encryptionDecryptionService;

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

        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

        var authorization = request.getHeader("Authorization");

        if (isShoppingOriginal(request)) {

            this.setFailedRequest(HttpStatus.NOT_FOUND, "{\"status\": 404,\"message\": \"Not found\",\"error\": null}");

        }

        /**
         * Check each of endpoint that they need user access token
         */
        if (authorization != null && authorization.contains("Bearer") && !isNotRequiredAuthorization(request)) {

            var auth = authService.checkToken(authorization);

            if (auth == AuthConstant.REVOKED) { // user logged out
                this.setFailedRequest(HttpStatus.UNAUTHORIZED, "{\"status\": 401,\"message\": \"Unauthorized\",\"error\": null}");
            }

            if (auth == AuthConstant.PERMISSION_CHANGED) { // user permission have changed by someone
                this.setFailedRequest(HttpStatus.FORBIDDEN, "{\"status\": 403,\"message\": \"Forbidden\",\"error\": null}");
            }
            this.checkRoleUser(request);
            this.validationCompanyId(request, authorization);
            stakeholderService.checkUserContact(ctx);

        }



        //============= Encrypt data for skyPoint
        encryptionDecryptionService.decryptRequest(request, ctx);

        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Reports an error message given a response body and code.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @param body
     */
    private void setFailedRequest(HttpStatus status, String body) {

        log.debug("Reporting error ({}): {}", status.value(), body);

        RequestContext ctx = getCurrentContext();
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
     *
     * @param request
     * @param token
     */
    private void validationCompanyId(HttpServletRequest request, String token) {

        Long companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty())
                ? Long.valueOf(request.getHeader("X-CompanyId"))
                : 0;

        if (companyId != 0) {
            String tokenId = token.substring("Bearer".length() + 1);

            Long tokenCompanyId = authService.getClaim(tokenId, "companyId", Long.class);

            if (!companyId.equals(tokenCompanyId)) {
                this.setFailedRequest(HttpStatus.FORBIDDEN, "{\"status\": 403,\"message\": \"Forbidden\",\"error\": null}");
            }
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check if shopping original endpoint
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    private boolean isShoppingOriginal(HttpServletRequest request) {

        var url = new String(request.getRequestURL());

        if (url.matches("http://apiv2.skybooking.net/flight/v1.0.0/shopping/search(.*)") && request.getMethod().equals("GET")) {

            return true;

        }

        return false;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check each endpoints not required user authentication
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    private boolean isNotRequiredAuthorization(HttpServletRequest request) {

        var uri = new String(request.getRequestURI());

        if (uri.matches("/stakeholder/(.*)v1.0.0/auth(.*)")
                || uri.matches("/stakeholder/(.*)v1.0.0/utils(.*)")
                || uri.matches("/staff/(.*)v1.0.0/auth(.*)")
                || uri.matches("/staff/(.*)v1.0.0/utils(.*)")
                || uri.matches("/flight/v1.0.0/shopping/flight/test-revalidate(.*)")
                || uri.matches("/flight/v1.0.0/shopping/flight/detail(.*)")
                || uri.matches("/flight/v1.0.0/shopping/search(.*)")
                || uri.matches("/flight/v1.0.0/sb(.*)")
                || uri.matches("/flight/v1.0.0/payment(.*)")
                || uri.matches("/flight/v1.0.0/ticketing(.*)")
                || uri.matches("/payment/ipay88/response(.*)")
                || uri.matches("/payment/pipay/success(.*)")
                || uri.matches("/payment/pipay/fail(.*)")
                || uri.matches("/payment/pipay/form(.*)")
                || uri.matches("/payment/ipay88/form(.*)")
                || uri.matches("/skyhistory/wv1.0.0/payment-success/no-auth(.*)")
                || uri.matches("/skyhistory/wv1.0.0/receipt-itinerary(.*)")
                || uri.matches("/skypoint/v(.*)/top-up/online/post/(.*)")
                || uri.matches("/skypoint/v(.*)/transaction/detail")) {

            return true;

        }

        return false;
    }

    public void checkRoleUser(HttpServletRequest request) {
        if (staffAuthorizationRoute(request)) {
            String userType = jwtUtils.getClaim("userType", String.class);
            String userRole = jwtUtils.getClaim("userRole", String.class);
            if (userType.equals("skyuser")) {
                this.setFailedRequest(HttpStatus.UNAUTHORIZED, "{\"status\": 401,\"message\": \"Unauthorized\",\"error\": null}");
            }
            if (userType.equals("skystaff") && !userRole.equals("admin")) {
                this.setFailedRequest(HttpStatus.UNAUTHORIZED, "{\"status\": 401,\"message\": \"Unauthorized\",\"error\": null}");
            }
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check each endpoints not required user authentication
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    private boolean staffAuthorizationRoute(HttpServletRequest request) {

        var uri = new String(request.getRequestURI());

        if (uri.matches("/staff/(.*)v1.0.0/find-skyuser")
                || uri.matches("/staff/(.*)v1.0.0/invite-skyuser")
                || uri.matches("/staff/(.*)v1.0.0/invite-skyuser-no-acc")
                || uri.matches("/staff/(.*)v1.0.0/list-pending-email")
                || uri.matches("/staff/(.*)v1.0.0/list-pending-email(.*)")
                || uri.matches("/staff/(.*)v1.0.0/list-pending-email/resend")) {

            return true;

        }

        return false;
    }
}
