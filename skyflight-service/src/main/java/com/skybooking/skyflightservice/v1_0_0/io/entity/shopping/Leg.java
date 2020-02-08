package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Leg implements Serializable {

    @Id
    private String id;
    private long duration;
    private String departure;
    private String departureTime;
    private String departureTerminal;
    private String arrival;
    private String arrivalTime;
    private String arrivalTerminal;
    private List<LegAirline> airlines = new ArrayList<>();
    private boolean multiAir;
    private List<LegSegmentDetail> segments = new ArrayList<>();
    private String price;
    private String baggage;
    private boolean isDirectFlight;
    private int legIndex;
    private int directionIndex;

}
