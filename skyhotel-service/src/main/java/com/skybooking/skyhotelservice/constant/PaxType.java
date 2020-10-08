package com.skybooking.skyhotelservice.constant;

public enum PaxType {
    CH("CH", "CHILD"),
    AD("AD", "ADULT");

    private final String key;
    private final String value;

    PaxType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public static String getValueFromKey(String key) {
        if (key.equals(PaxType.CH.getKey()))
            return PaxType.CH.getValue();
        return PaxType.AD.getValue();
    }

    public static PaxType getObjectByKey(String key) {
        if (key.equals(PaxType.CH.getKey()))
            return PaxType.CH;
        return PaxType.AD;
    }
}
