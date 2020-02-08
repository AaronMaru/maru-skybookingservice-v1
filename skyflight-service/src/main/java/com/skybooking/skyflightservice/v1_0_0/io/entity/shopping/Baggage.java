package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class Baggage implements Serializable {

    private String type;
    private int pieces;
    private int weights;
    private String unit;
    private boolean nonRefundable;
    private boolean isPiece;

}
