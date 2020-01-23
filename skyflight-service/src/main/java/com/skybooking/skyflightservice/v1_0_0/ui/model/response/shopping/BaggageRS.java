package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaggageRS implements Serializable {

    private String type = "";
    private int piece;
    private int weight;
    private String unit = "";
    private boolean nonRefundable;

}
