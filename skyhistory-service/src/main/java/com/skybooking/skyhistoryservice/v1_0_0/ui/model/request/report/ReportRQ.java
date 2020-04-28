package com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.report;

import com.skybooking.skyhistoryservice.v1_0_0.util.auth.UserToken;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class ReportRQ {

    private Long userId;
    private Long companyId;
    private Long companyHeaderId;
    private String userType;
    private String userRole;
    private String classType;
    private String tripType;
    private String startDate;
    private String endDate;
    private int page;
    private int size;
    private Long skystaffId;

    public ReportRQ(HttpServletRequest request, UserToken userToken) {

        this.userId = userToken.getStakeholderId();
        this.companyId = userToken.getCompanyId();
        this.userRole = userToken.getUserRole().toUpperCase();
        this.companyHeaderId = userToken.getCompanyId();
        this.classType = request.getParameter("classType") != null && !request.getParameter("classType").equals("") ? request.getParameter("classType").toUpperCase() : "ALL";
        this.tripType = request.getParameter("tripType") != null && !request.getParameter("tripType").equals("") ? request.getParameter("tripType").toUpperCase() : "ALL";
        this.startDate = request.getParameter("startDate") != null && !request.getParameter("startDate").equals("") ? request.getParameter("startDate") : "ALL";
        this.endDate = request.getParameter("endDate") != null && !request.getParameter("endDate").equals("") ? request.getParameter("endDate") : "ALL";
        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;
        this.skystaffId = request.getParameter("skystaffId") != null && !request.getParameter("skystaffId").isEmpty() ? Long.valueOf(request.getParameter("skystaffId")) : 0;
    }

}