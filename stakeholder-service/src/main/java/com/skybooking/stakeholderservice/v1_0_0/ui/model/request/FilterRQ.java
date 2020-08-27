package com.skybooking.stakeholderservice.v1_0_0.ui.model.request;

import com.skybooking.stakeholderservice.v1_0_0.util.auth.UserToken;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class FilterRQ {

    private int size;
    private int page;
    private Boolean readAll;
    private Long skyuserId;
    private Long companyHeaderId;
    private String role;

    public FilterRQ(HttpServletRequest request, UserToken userToken) {

        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;

        this.readAll = request.getParameter("readAll") != null && !request.getParameter("readAll").isEmpty() ? Boolean.valueOf(request.getParameter("readAll")) : false;

        this.role = userToken.getUserRole();
        this.skyuserId = userToken.getStakeholderId();
        this.companyHeaderId = request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty() ? Long.valueOf(request.getHeader("X-CompanyId")): 0;

    }
}
