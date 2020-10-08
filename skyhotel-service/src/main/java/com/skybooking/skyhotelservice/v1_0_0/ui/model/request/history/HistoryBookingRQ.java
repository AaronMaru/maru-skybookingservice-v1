package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.history;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class HistoryBookingRQ {
    private String keywordSearch;
    private String bookedBy;
    private String checkIn;
    private String checkOut;
    private Integer roomNumber;
    private String bookingStatus;
    private String bookingDateFrom;
    private String bookingDateTo;
    private String paymentType;
    private Integer priceRangeStart;
    private Integer priceRangeEnd;
    private int page;
    private int size;

    public HistoryBookingRQ(HttpServletRequest request) {
        this.keywordSearch = request.getParameter("keywordSearch") != null ? request.getParameter("keywordSearch") : "";
        this.bookedBy = request.getParameter("bookedBy") != null ? request.getParameter("bookedBy") : "";
        this.checkIn = request.getParameter("checkIn") != null ? request.getParameter("checkIn") : "";
        this.checkOut = request.getParameter("checkOut") != null ? request.getParameter("checkOut") : "";
        this.roomNumber = request.getParameter("roomNumber") != null ? Integer.valueOf(request.getParameter("roomNumber")) : 0;
        this.bookingStatus = request.getParameter("bookingStatus") != null ? request.getParameter("bookingStatus") : "";
        this.bookingDateFrom = request.getParameter("bookingDateFrom") != null ? request.getParameter("bookingDateFrom") : "";
        this.bookingDateTo = request.getParameter("bookingDateTo") != null ? request.getParameter("bookingDateTo") : "";
        this.paymentType = request.getParameter("paymentType") != null ? request.getParameter("paymentType") : "";
        this.priceRangeStart = request.getParameter("priceRangeStart") != null ? Integer.valueOf(request.getParameter("priceRangeStart")) : 0;
        this.priceRangeEnd = request.getParameter("priceRangeEnd") != null ? Integer.valueOf(request.getParameter("priceRangeEnd")) : 1000000;
        this.page = request.getParameter("page") != null && !request.getParameter("page").isEmpty() ? ( Integer.valueOf(request.getParameter("page") ) - 1 ) : 0;
        this.size = request.getParameter("size") != null && !request.getParameter("size").isEmpty() ? Integer.valueOf(request.getParameter("size")) : 10;
    }

}
