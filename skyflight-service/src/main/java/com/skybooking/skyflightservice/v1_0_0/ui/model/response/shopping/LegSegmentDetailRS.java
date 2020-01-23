package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegSegmentDetailRS implements Serializable {

    private String segment;
    private int stop;
    private long duration;
    private long layOverDuration;
    private int dateAdjustment;
    private boolean availabilityBreak;

}
