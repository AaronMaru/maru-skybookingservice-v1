package com.skybooking.skyhotelservice.constant;

public enum CancellationTypeConstant {
    FREE("Free cancellation"),
    FREE_BEFORE("Free cancellation before {{datetime}}"),
    PARTIAL("Partial cancellation fees"),
    NON_REFUNDABLE("Non refundable");

    String value;

    CancellationTypeConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CancellationTypeConstant getName(String name) {
        if (name.equals(FREE.name()))
            return FREE;
        if (name.equals(PARTIAL.name()))
            return PARTIAL;
        return NON_REFUNDABLE;
    }
}
