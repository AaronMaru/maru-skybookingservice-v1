package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightShoppingRS {

    private List<AirlineRS> airlines = new ArrayList<>();
    private List<AircraftRS> aircrafts = new ArrayList<>();
    private List<LocationRS> locations = new ArrayList<>();
    private List<PriceRS> prices = new ArrayList<>();
    private List<BaggageRS> baggages = new ArrayList<>();
    private List<SegmentRS> segments = new ArrayList<>();
    private List<LegRS> legs = new ArrayList<>();
    private List<ItineraryRS> direct = new ArrayList<>();
    private List<ItineraryRS> cheapest = new ArrayList<>();

}
