package com.skybooking.skyhotelservice.v1_0_0.client.model.request.history;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.history.HistoryBookingRQ;
import lombok.Data;

import java.util.List;

@Data
public class HistoryHBRQ {

    List<String> bookingCodes = List.of();

    private FilterRQ filter;

    public HistoryHBRQ(){}
    public HistoryHBRQ(List<String> bookingCodes) {
        this.bookingCodes = bookingCodes;
    }

    public HistoryHBRQ(List<String> bookingCodes, FilterRQ filterRQ) {
        this.bookingCodes = bookingCodes;
        this.filter = filterRQ;
    }


}
