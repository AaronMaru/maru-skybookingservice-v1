package com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking;

import com.skybooking.skyhistoryservice.v1_0_0.util.auth.UserToken;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class FilterRQ {

    private String keyword;
    private String bookStatus;
    private Integer startRange;
    private Integer endRange;
    private String tripType;
    private String className;
    private String bookDate;
    private String paymentType;
    private String flyingFrom;
    private String flyingTo;
    private String bookByName;
    private String action;
    private String role;
    private Long skyuserId;
    private Long companyId;
    private int page;
    private int size;

    public FilterRQ(HttpServletRequest request, UserToken userToken) {

        this.keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        this.bookStatus = request.getParameter("bookStatus") != null ? request.getParameter("bookStatus") : "all";
        this.startRange = request.getParameter("startRange") != null ? Integer.valueOf(request.getParameter("startRange")) : 0;
        this.endRange = request.getParameter("endRange") != null ? Integer.valueOf(request.getParameter("endRange")) : 1000000;
        this.tripType = request.getParameter("tripType") != null ? request.getParameter("tripType") : "all";
        this.className = request.getParameter("className") != null ? request.getParameter("className") : "all";
        this.bookDate = request.getParameter("bookDate") != null ? request.getParameter("bookDate") : "all";
        this.paymentType = request.getParameter("paymentType") != null ? request.getParameter("paymentType") : "all";
        this.flyingFrom = request.getParameter("flyingFrom") != null ? request.getParameter("flyingFrom") : "all";
        this.flyingTo = request.getParameter("flyingTo") != null ? request.getParameter("flyingTo") : "all";
        this.bookByName = request.getParameter("bookByName") != null ? request.getParameter("bookByName") : "all";
        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;

        System.out.println(userToken.getUserRole());

        this.role = userToken.getUserRole();
        this.skyuserId = userToken.getStakeholderId();
        this.companyId = userToken.getCompanyId();
    }
}
