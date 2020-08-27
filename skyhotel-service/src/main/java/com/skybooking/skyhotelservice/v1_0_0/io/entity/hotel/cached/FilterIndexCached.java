package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter.FilterFacilityCached;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class FilterIndexCached implements Serializable {
    private Map<String, FilterFacilityCached> facilities;
    private Map<String, List<Integer>> boards;
}
