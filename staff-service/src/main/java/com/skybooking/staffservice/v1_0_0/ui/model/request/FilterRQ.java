package com.skybooking.staffservice.v1_0_0.ui.model.request;

import com.skybooking.staffservice.constant.StaffStatusConstant;
import com.skybooking.staffservice.v1_0_0.util.auth.UserToken;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class FilterRQ {



    private String keyword;
    private Integer startPRange;
    private Integer endPRange;
    private String joinDate;
    private String joinStatus;
    private int page;
    private int size;
    private String action;
    private String role;
    private Long skyuserId;
    private Long companyId;
    private String active;
    private String inactive;
    private String banned;

    public FilterRQ(HttpServletRequest request, UserToken userToken) {

        this.keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        this.role = request.getParameter("role") != null ? request.getParameter("role").toUpperCase() : "ALL";
        this.startPRange = request.getParameter("startPRange") != null ? Integer.valueOf(request.getParameter("startPRange")) : 0;
        this.endPRange = request.getParameter("endPRange") != null ? Integer.valueOf(request.getParameter("endPRange")) : 1000000;
        this.joinDate = request.getParameter("joinDate") != null ? request.getParameter("joinDate") : "ALL";
        this.joinStatus = request.getParameter("joinStatus") != null ? request.getParameter("joinStatus").toUpperCase() : "ALL";
        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;

        StaffStatusConstant staffContant = new StaffStatusConstant();
        this.active = staffContant.ACTIVE;
        this.inactive = staffContant.INACTIVE;
        this.banned = staffContant.BANNED;

        this.skyuserId = userToken.getStakeholderId();
        this.companyId = userToken.getCompanyId();

    }

}
