package com.skybooking.skyhotelservice.constant;

import java.util.List;

public enum SearchByConstant {
    DESTINATION,
    GEOLOCATION;

    private static final String[] DESTINATION_TYPE = {"CITY", "HOTEL", "AIRPORT", "HARBOUR", "RAILWAY_STATION"};
    private static final String[] GEOLOCATION_TYPE = {"LANDMARK"};

    public static SearchByConstant getSearchBy(String type) {
        if (List.of(DESTINATION_TYPE).contains(type))
            return DESTINATION;
        return GEOLOCATION;
    }
}
