package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoomCached implements Serializable {
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
    private List<RoomStayCached> roomStays = null;
    private List<RateCached> rates = null;
    private List<Object> roomFacilities = null;
}
