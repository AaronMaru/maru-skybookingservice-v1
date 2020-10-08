package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class ReserveRQ {
    private String requestId;
    private String checkIn;
    private String checkOut;
    private Integer hotelCode;
    private ReserveHolderRQ holder;
    private List<CheckRateRoomRateRQ> rooms;
}
