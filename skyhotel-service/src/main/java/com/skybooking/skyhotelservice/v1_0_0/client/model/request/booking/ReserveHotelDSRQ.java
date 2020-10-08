package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class ReserveHotelDSRQ {
    private Integer code;
    private String checkIn;
    private String checkOut;
    private HolderDSRQ holder;
    private List<ReserveRoomDSRQ> rooms;
}
