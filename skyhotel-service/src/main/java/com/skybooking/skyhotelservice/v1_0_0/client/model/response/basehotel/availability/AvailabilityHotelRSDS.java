package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.review.ReviewRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRSDS;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AvailabilityHotelRSDS {

    private Integer code;
//    private BigDecimal minRate = BigDecimal.ZERO;
//    private BigDecimal maxRate = BigDecimal.ZERO;
//    private String currency;
//    private List<RoomRSDS> rooms;
    private String categoryCode;
    private List<RoomRSDS> rooms;
    private List<ReviewRSDS> reviews;

}
