package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegSegmentDetailRS implements Serializable {

    private String segment;
    private long duration;
    private long layoverDuration;
    private String layoverAirport;
    private int previousDateAdjustment;
    private int dateAdjustment;
    private int stops;
    private int seatsRemain;
    private boolean availabilityBreak;
    private String cabin;
    private String cabinName;
    private String meal;
    private String mealName;

}
