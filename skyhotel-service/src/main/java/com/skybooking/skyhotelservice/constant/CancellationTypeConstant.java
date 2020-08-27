package com.skybooking.skyhotelservice.constant;

public enum CancellationTypeConstant {
    FREE,
    PARTIAL,
    NON_REFUNDABLE;

    public static CancellationTypeConstant getName(String name) {
        if (name.equals(FREE.name()))
            return FREE;
        if (name.equals(PARTIAL.name()))
            return PARTIAL;
        return NON_REFUNDABLE;
    }
}
