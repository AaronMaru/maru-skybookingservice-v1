package com.skybooking.skyhotelservice.v1_0_0.util.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderCM {

    @Autowired
    private HttpServletRequest request;

    public Long getCompanyId() {
        return (request.getHeader("X-CompanyId") != null ) ? (long) Integer.parseInt(request.getHeader("X-CompanyId")) : null;
    }

}
