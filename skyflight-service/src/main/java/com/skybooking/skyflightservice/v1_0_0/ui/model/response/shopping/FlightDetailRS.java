package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightDetailRS {

    private List<AirlineRS> airlines = new ArrayList<>();
    private List<AircraftRS> aircrafts = new ArrayList<>();
    private List<LocationRS> locations = new ArrayList<>();
    private List<PriceDetailRS> prices = new ArrayList<>();
    private List<BaggageDetailRS> baggages = new ArrayList<>();
    private List<SegmentRS> segments = new ArrayList<>();
    private List<LegRS> legs = new ArrayList<>();

}
