package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import lombok.Data;

import java.util.List;

@Data
public class RoomRSDS {
    private String roomCode;
    private String name;
    private List<RateRSDS> rates;
}
