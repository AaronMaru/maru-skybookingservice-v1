package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegAirlineRS implements Serializable {

    private String airline = "";
    private String aircraft = "";
    private String flightNumber = "";

}
