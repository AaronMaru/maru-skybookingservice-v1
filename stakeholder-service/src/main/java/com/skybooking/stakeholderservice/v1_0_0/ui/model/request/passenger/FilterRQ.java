package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger;

import com.skybooking.stakeholderservice.v1_0_0.util.auth.UserToken;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class FilterRQ {

    private int size;
    private int page;

    private Long companyId;
    private Long skyuserId;
    private String role;

    public FilterRQ(HttpServletRequest request, UserToken userToken) {

        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;

        this.role = userToken.getUserRole();
        this.skyuserId = userToken.getStakeholderId();
        this.companyId = userToken.getCompanyId();


    }
}
