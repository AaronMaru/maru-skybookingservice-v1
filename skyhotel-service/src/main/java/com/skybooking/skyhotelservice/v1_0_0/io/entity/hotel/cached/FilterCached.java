package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterCodeCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterFloatCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterNumberCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterPriceCached;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterCached implements Serializable {
    private FilterPriceCached priceRange = new FilterPriceCached();
    private List<FilterCodeCached> boards = new ArrayList<>();
    private List<FilterNumberCached> stars = new ArrayList<>();
    private List<FilterFloatCached> scores = new ArrayList<>();
    private List<FilterFloatCached> cancellations = new ArrayList<>();
    private List<FilterFloatCached> zones = new ArrayList<>();
    private List<FilterFloatCached> establishmentProfile = new ArrayList<>();
    private List<FilterFloatCached> amenities = new ArrayList<>();
    private List<FilterFloatCached> accommodationTypes = new ArrayList<>();
    private List<FilterFloatCached> discounts = new ArrayList<>();
    private List<FilterFloatCached> topHotels = new ArrayList<>();
    private List<FilterFloatCached> chainNames = new ArrayList<>();
}
