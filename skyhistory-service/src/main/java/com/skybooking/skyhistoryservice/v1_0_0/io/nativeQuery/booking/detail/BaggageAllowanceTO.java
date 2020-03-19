package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

@Data
public class BaggageAllowanceTO {

    private String passType;
    private int isPiece;
    private int piece;
    private Integer weight;
    private String unit;

}
