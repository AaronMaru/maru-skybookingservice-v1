package com.skybooking.skyflightservice.constant;

public enum TripTypeEnum {

    ONEWAY,
    ROUND,
    MULTICITY;

    public static String info(TripTypeEnum type) {

        switch (type) {

            case ONEWAY:
                return TripTypeConstant.ONEWAY;

            case ROUND:
                return TripTypeConstant.ROUND;

            default:
                return TripTypeConstant.MULTICITY;
        }
    }
}
