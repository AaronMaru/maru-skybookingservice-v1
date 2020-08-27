package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomStayCached implements Serializable {
    private Integer hotelCode;
    private String roomCode;
    private String stayTypeCode;
    private String roomStayName;
}
