package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilterFacilityCached implements Serializable {
    private String key;
    private String icon;
    private String name;
    private List<Integer> hotels;
}
