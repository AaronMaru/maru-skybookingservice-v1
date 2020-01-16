package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LegRS {

    private String id;
    private int duration;
    private String departure;
    private String departureTime;
    private String departureTerminal;
    private String arrival;
    private String arrivalTime;
    private String arrivalTerminal;
    private String airline;
    private boolean multiAir;
    private List<String> segments = new ArrayList<>();
    private String adultBaggageRef;
    private String adultPriceRef;
    private String childBaggageRef;
    private String childPriceRef;
    private String infantBaggageRef;
    private String infantPriceRef;
}
