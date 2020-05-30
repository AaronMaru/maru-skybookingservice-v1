package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightShoppingRevalidateRQ {

    private int adult;

    private int child;

    private int infant;

    @JsonProperty("trip")
    private String tripType;

    @JsonProperty("class")
    private String classType;

    private List<FlightLegShoppingRevalidateRQ> legs = new ArrayList<>();

    private List<String> airlines = new ArrayList<>();

}
