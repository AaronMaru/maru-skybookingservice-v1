package com.skybooking.skyhotelservice.v1_0_0.client.model.request.history;

import lombok.Data;

import java.util.List;

@Data
public class HistoryDSRQ {

    List<String> bookingCodes = List.of();

    private FilterRQ filter = new FilterRQ();

    public HistoryDSRQ(){}
    public HistoryDSRQ(List<String> bookingCodes) {
        this.bookingCodes = bookingCodes;
    }

    public HistoryDSRQ(List<String> bookingCodes, FilterRQ filterRQ) {
        this.bookingCodes = bookingCodes;
        this.filter = filterRQ;
    }


}
