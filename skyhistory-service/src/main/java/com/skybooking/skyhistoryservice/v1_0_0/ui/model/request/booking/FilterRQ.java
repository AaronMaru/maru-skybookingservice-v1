package com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
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
    private Long companyHeaderId;
    private int page;
    private int size;
    private String completed;
    private String upcoming;
    private String cancelled;
    private String failed;
    private String oneway;
    private String round;
    private String multicity;

    public FilterRQ(HttpServletRequest request, UserToken userToken) {

        this.keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        this.bookStatus = request.getParameter("bookStatus") != null ? request.getParameter("bookStatus").toUpperCase() : "ALL";
        this.startRange = request.getParameter("startRange") != null ? Integer.valueOf(request.getParameter("startRange")) : 0;
        this.endRange = request.getParameter("endRange") != null ? Integer.valueOf(request.getParameter("endRange")) : 1000000;
        this.tripType = request.getParameter("tripType") != null ? request.getParameter("tripType").toUpperCase() : "ALL";
        this.className = request.getParameter("className") != null ? request.getParameter("className").toUpperCase() : "ALL";
        this.bookDate = request.getParameter("bookDate") != null ? request.getParameter("bookDate").toUpperCase() : "ALL";
        this.paymentType = request.getParameter("paymentType") != null ? request.getParameter("paymentType").toUpperCase() : "ALL";
        this.flyingFrom = request.getParameter("flyingFrom") != null ? request.getParameter("flyingFrom").toUpperCase() : "ALL";
        this.flyingTo = request.getParameter("flyingTo") != null ? request.getParameter("flyingTo").toUpperCase() : "ALL";
        this.bookByName = request.getParameter("bookByName") != null ? request.getParameter("bookByName").toUpperCase() : "ALL";
        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;
        this.companyHeaderId = request.getHeader("X-CompanyId") != null ? Long.valueOf(request.getHeader("X-CompanyId")): 0;

        BookingKeyConstant constant = new BookingKeyConstant();
        this.completed = constant.COMPLETED;
        this.upcoming = constant.UPCOMING;
        this.cancelled = constant.CANCELLED;
        this.failed = constant.FAILED;
        this.oneway = constant.ONEWAY;
        this.round = constant.ROUND;
        this.multicity = constant.MULTICITY;

        this.role = userToken.getUserRole().toUpperCase();
        this.skyuserId = userToken.getStakeholderId();
        this.companyId = userToken.getCompanyId();

    }
}
