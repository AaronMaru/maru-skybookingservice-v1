package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.review.ReviewRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRSDS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyRSDS {

    private String categoryCode;
    private String categoryName;
    private String minRate;
    private String maxRate;
    private List<RoomRSDS> rooms = new ArrayList<>();
    private List<ReviewRSDS> reviews = new ArrayList<>();

}
