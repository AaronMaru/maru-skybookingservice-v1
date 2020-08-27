package com.skybooking.skyhotelservice.constant;

public enum EnumBookingStatus {

    PRECONFIRMED(1, "PRECONFIRMED"),
    CONFIRMED(2, "CONFIRMED"),
    CANCELLED(3, "CANCELLED");

    private final Integer key;
    private final String value;

    EnumBookingStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    public static EnumBookingStatus getStatusByKey(Integer key) {
        if (key == EnumBookingStatus.PRECONFIRMED.getKey()) {
            return EnumBookingStatus.PRECONFIRMED;
        }
        if (key == EnumBookingStatus.CANCELLED.getKey()) {
            return EnumBookingStatus.CANCELLED;
        }
        return EnumBookingStatus.CONFIRMED;
    }
}
