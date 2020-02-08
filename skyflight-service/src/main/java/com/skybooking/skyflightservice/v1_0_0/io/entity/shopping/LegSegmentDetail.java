package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegSegmentDetail implements Serializable {

    private String segment;
    private int stops;
    private long duration;
    private long layoverDuration;
    private int dateAdjustment;
    private String bookingCode;
    private int seatsRemain;
    private String cabin;
    private String cabinName;
    private String meal;
    private String mealName;
    private boolean availabilityBreak;

}
