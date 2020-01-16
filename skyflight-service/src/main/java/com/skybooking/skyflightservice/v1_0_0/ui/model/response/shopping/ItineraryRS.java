package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryRS {

    private String airlineRef;
    private List<LegGroupRS> legsGroup = new ArrayList<>();

}
