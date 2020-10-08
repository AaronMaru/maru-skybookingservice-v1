package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class CheckRateRoomRateRQ {
    private int rooms;
    private int adults;
    private int children;
    private String rateKey;
    private List<BookingPaxRQ> paxes;
}
