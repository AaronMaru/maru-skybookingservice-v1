package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightLegShoppingRevalidateRQ {

    private String origin;

    private String destination;

    private String departureDateTime;

    private String departureWindow;

    private List<String> airlines = new ArrayList<>();
}
