package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterRQ {

    @Valid
    private FilterPriceRQ priceRange;
    private List<String> boards = new ArrayList<>();

    @Valid
    private List<@Min(0) @Max(5) Number> stars = new ArrayList<>();

    @Valid
    private List<@Min(0) @Max(5) Number> scores = new ArrayList<>();

    private List<String> cancellations = new ArrayList<>();
    private List<String> zones = new ArrayList<>();
    private List<Number> establishmentProfile = new ArrayList<>();
    private List<String> amenities = new ArrayList<>();
    private List<String> accommodationTypes = new ArrayList<>();
    private List<String> discounts = new ArrayList<>();
    private List<String> topHotels = new ArrayList<>();
    private List<String> chainNames = new ArrayList<>();
    private List<String> promotions = new ArrayList<>();

}
