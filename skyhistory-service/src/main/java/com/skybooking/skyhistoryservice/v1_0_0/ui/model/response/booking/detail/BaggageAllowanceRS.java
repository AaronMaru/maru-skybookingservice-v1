package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

@Data
public class BaggageAllowanceRS {

    private String passType;
    private int isPiece;
    private int piece;
    private Integer weight;
    private String unit;

}
