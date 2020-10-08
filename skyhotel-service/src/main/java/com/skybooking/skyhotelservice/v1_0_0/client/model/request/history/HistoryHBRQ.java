package com.skybooking.skyhotelservice.v1_0_0.client.model.request.history;

import lombok.Data;

import java.util.List;

@Data
public class HistoryHBRQ {

    List<String> bookingCodes = List.of();

    public HistoryHBRQ(){}
    public HistoryHBRQ(List<String> bookingCodes) {
        this.bookingCodes = bookingCodes;
    }

}
