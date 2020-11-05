package com.skybooking.skygatewayservice.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PermissionCM {


    public boolean admin (HttpServletRequest request) {

        var uri = new String(request.getRequestURI());
        if (uri.matches("/skypoint/(.*)v1.0.0/account/balance")
                || uri.matches("/skypoint/(.*)v1.0.0/limit-point/available/point")
                || uri.matches("/skypoint/(.*)v1.0.0/history/sky-owner/transaction")
                || uri.matches("/skypoint/(.*)v1.0.0/payment/redeem/confirm")
                || uri.matches("/skypoint/(.*)v1.0.0/limit-point/create")
                || uri.matches("/skypoint/(.*)v1.0.0/top-up/online/proceed")) {
            return true;
        }
        return false;

    }

    public boolean moderator (HttpServletRequest request) {
        var uri = new String(request.getRequestURI());
        if (uri.matches("")
                || uri.matches("/skypoint/(.*)v1.0.0/limit-point/available/point")
                || uri.matches("/skypoint/(.*)v1.0.0/history/sky-owner/transaction")
                || uri.matches("/skypoint/(.*)v1.0.0/payment/redeem/confirm")) {
            return true;
        }
        return false;
    }

    public boolean editor (HttpServletRequest request) {
        var uri = new String(request.getRequestURI());
        if (uri.matches("")
                || uri.matches("/skypoint/(.*)v1.0.0/limit-point/available/point")
                || uri.matches("/skypoint/(.*)v1.0.0/history/sky-owner/transaction")
                || uri.matches("/skypoint/(.*)v1.0.0/payment/redeem/confirm")) {
            return true;
        }
        return false;
    }
}
