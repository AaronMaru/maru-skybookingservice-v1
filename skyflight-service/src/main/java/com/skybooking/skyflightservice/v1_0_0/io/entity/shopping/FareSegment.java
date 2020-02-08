package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

@Data
public class FareSegment {
    private String bookingCode;
    private int seatsRemain;
    private String cabin;
    private String cabinName;
    private String meal;
    private String mealName;
    private boolean availabilityBreak;
}
