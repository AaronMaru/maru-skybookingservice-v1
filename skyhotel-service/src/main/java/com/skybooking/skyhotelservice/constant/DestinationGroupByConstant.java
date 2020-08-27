package com.skybooking.skyhotelservice.constant;

public enum DestinationGroupByConstant {
    CONTINENT("CONTINENT"),
    COUNTRY("COUNTRY");

    private final String value;

    DestinationGroupByConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static DestinationGroupByConstant getName(String value) {
        if (value.equals(CONTINENT.getValue()))
            return CONTINENT;
//        if (value.equals(COUNTRY.getValue()))
            return COUNTRY;
    }
}
