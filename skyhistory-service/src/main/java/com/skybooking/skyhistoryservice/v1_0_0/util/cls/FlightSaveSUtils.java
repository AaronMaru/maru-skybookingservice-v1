package com.skybooking.skyhistoryservice.v1_0_0.util.cls;

import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class FlightSaveSUtils extends SortUtils {

    public static Sort sort(String by, String order) {
        return sortBy(Arrays.asList("classCode", "className", "tripType", "amount"), by, order);
    }
}
