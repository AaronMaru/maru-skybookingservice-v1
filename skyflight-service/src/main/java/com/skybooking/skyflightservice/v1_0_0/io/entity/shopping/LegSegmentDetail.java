package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegSegmentDetail implements Serializable {

    private String segment;
    private int stop;
    private long duration;
    private long layOverDuration;
    private int dateAdjustment;
    private boolean availabilityBreak;

}
