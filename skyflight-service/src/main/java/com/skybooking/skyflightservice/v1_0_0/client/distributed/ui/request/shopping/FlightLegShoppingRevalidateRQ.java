package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import lombok.Data;

@Data
public class FlightLegShoppingRevalidateRQ {

    private String origin;

    private String destination;

    private String departureDateTime;
}
