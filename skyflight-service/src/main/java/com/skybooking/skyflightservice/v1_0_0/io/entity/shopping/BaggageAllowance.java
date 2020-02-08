package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaggageAllowance implements Serializable {

    private int pieces;
    private String unit;
    private int weights;
    private boolean isPiece;

}
