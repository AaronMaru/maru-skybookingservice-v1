package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content;

import lombok.Data;

import java.util.List;

@Data
public class RoomRSDS {
    private Integer hotelCode;
    private String roomCode;
    private String roomName;
    private Integer minPax;
    private Integer maxPax;
    private Integer minAdults;
    private Integer maxAdults;
    private Integer maxChildren;
    private String roomType;
    private String roomTypeName;
    private String characteristicCode;
    private String characteristicName;
    private List<RoomStayRSDS> roomStays = null;
    private List<Object> roomFacilities = null;
}
