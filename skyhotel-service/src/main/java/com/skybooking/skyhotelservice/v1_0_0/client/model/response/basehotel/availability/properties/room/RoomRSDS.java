package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room;

import lombok.Data;

import java.util.List;

@Data
public class RoomRSDS {

    private String code;
    private String name;
    private List<RoomRateRSDS> rates;

}
