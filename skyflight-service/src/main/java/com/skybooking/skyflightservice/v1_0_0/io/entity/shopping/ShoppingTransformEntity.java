package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingTransformEntity implements Serializable {

    private String requestId;
    private String trip;
    private List<Airline> airlines = new ArrayList<>();
    private List<Aircraft> aircrafts = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<LayoverAirport> layoverAirports = new ArrayList<>();
    private List<PriceDetail> prices = new ArrayList<>();
    private List<BaggageDetail> baggages = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();
    private List<Leg> legs = new ArrayList<>();
    private List<Itinerary> direct = new ArrayList<>();
    private List<Itinerary> cheapest = new ArrayList<>();

}
