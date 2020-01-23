package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class LegRS implements Serializable {

    private String id;
    private int duration;
    private String departure = "";
    private String departureTime = "";
    private String departureTerminal = "";
    private String arrival = "";
    private String arrivalTime = "";
    private String arrivalTerminal = "";
    private List<LegAirlineRS> airlines = new ArrayList<>();
    private boolean multiAir;
    private List<LegSegmentDetailRS> segments = new ArrayList<>();
    private String price;
    private String baggage;

}
