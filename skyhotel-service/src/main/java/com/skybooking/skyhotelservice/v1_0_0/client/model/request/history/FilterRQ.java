package com.skybooking.skyhotelservice.v1_0_0.client.model.request.history;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.history.HistoryBookingRQ;
import lombok.Data;

@Data
public class FilterRQ {

    private String keyword = "";
    private String consumerCode = "";
    private String bookingDateFrom = "";
    private String bookingDateTo = "";
    private String destinationCode = "";
    private String boardCode = "";
    private String categoryCode = "";
    private String zoneCode = "";
    private Integer page = 1;
    private Integer size = 20;

    public FilterRQ () {}

    public FilterRQ (HistoryBookingRQ historyBookingRQ) {
        this.bookingDateFrom = historyBookingRQ.getBookingDateFrom();
        this.bookingDateTo = historyBookingRQ.getBookingDateTo();
        this.destinationCode = historyBookingRQ.getDestinationCode();
        this.boardCode = historyBookingRQ.getBoardCode();
        this.page = historyBookingRQ.getPage();
        this.size = historyBookingRQ.getSize();
    }
}
