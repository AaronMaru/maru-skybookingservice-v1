package com.skybooking.skyhotelservice.constant;

public enum SortTypeConstant {
    SORT_RECOMMENDED("RECOMMENDED"),
    SORT_PRICE_LOW_HIGH("PRICE"),
    SORT_PRICE_HIGH_LOW("PRICE"),
    SORT_STAR_HIGH_LOW("STAR"),
    SORT_STAR_LOW_HIGH("STAR"),
    SORT_GUEST_RATING("GUEST_RATING"),
    SORT_DISTANCE("DISTANCE");

    private final String value;

    SortTypeConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SortTypeConstant getName(String name) {
        if (name.equals(SORT_PRICE_LOW_HIGH.name()))
            return SORT_PRICE_LOW_HIGH;
        if (name.equals(SORT_PRICE_HIGH_LOW.name()))
            return SORT_PRICE_HIGH_LOW;
        if (name.equals(SORT_STAR_HIGH_LOW.name()))
            return SORT_STAR_HIGH_LOW;
        if (name.equals(SORT_STAR_LOW_HIGH.name()))
            return SORT_STAR_LOW_HIGH;
        if (name.equals(SORT_GUEST_RATING.name()))
            return SORT_GUEST_RATING;
        if (name.equals(SORT_DISTANCE.name()))
            return SORT_DISTANCE;
        return SORT_RECOMMENDED;
    }
}
