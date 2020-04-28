package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

@Data
public class BaggageAllowanceTO {

    private String type;
    private int piece;
    private int pieces;
    private Integer weights;
    private String unit;
    private Boolean nonRefundable;

}
