package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

@Data
public class BaggageAllowanceRS {

    private String type;
    private int pieces;
    private String unit;
    private Boolean piece;
    private Integer weights;
    private Boolean nonRefundable;

}
