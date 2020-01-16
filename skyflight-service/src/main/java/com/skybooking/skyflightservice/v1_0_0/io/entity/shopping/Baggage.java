package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class Baggage implements Serializable {

    private String type;
    private int piece;
    private int weight;
    private String unit;

}
