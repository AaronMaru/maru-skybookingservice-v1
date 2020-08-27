package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilterRS {

    private FilterPriceRS priceRange = new FilterPriceRS();
    private List<FilterCodeRS> boards = new ArrayList<>();
    private List<FilterNumberRS> stars = new ArrayList<>();
    private List<FilterNumberRS> scores = new ArrayList<>();
    private List<FilterCodeRS> cancellations = new ArrayList<>();
    private List<FilterCodeRS> zones = new ArrayList<>();
    private List<FilterNumberRS> establishmentProfile = new ArrayList<>();
    private List<FilterCodeRS> amenities = new ArrayList<>();
    private List<FilterCodeRS> accommodationTypes = new ArrayList<>();
    private List<FilterCodeRS> promotions = new ArrayList<>();
    private List<FilterCodeRS> topHotels = new ArrayList<>();
    private List<FilterCodeRS> chains = new ArrayList<>();

}
