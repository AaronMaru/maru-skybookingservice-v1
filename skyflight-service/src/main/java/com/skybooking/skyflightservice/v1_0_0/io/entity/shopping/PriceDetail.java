package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceDetail implements Serializable {

    private String id;
    private double total;
    private double totalAvg;
    private String currency;
    private List<Price> details = new ArrayList<>();

}
