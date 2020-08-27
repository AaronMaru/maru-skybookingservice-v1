package com.skybooking.skyhotelservice.constant;

import java.util.List;

public enum DestinationTypeGroupConstant {
    DESTINATION,
    GEOLOCATION,
    TERMINAL;

    private static final String[] DESTINATION_GROUP = {"CITY", "HOTEL"};
    private static final String[] TERMINAL_GROUP = {"AIRPORT", "HARBOUR", "RAILWAY_STATION"};
    private static final String[] GEOLOCATION_GROUP = {"LANDMARK"};

    public static DestinationTypeGroupConstant getGroup(String type) {
        if (List.of(GEOLOCATION_GROUP).contains(type))
            return GEOLOCATION;
        if (List.of(DESTINATION_GROUP).contains(type))
            return DESTINATION;
        return TERMINAL;
    }
}
