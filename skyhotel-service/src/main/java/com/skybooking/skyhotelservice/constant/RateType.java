package com.skybooking.skyhotelservice.constant;

public enum RateType {
    BOOKABLE,
    RECHECK;
    
    public static RateType getByName(String name) {
        if (BOOKABLE.name().equals(name))
            return BOOKABLE;
        return RECHECK;
    }
}
