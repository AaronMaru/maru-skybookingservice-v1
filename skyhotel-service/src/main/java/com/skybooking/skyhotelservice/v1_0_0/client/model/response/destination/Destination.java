package com.skybooking.skyhotelservice.v1_0_0.client.model.response.destination;

import lombok.Data;

@Data
public class Destination {
    private String destinationCode;
    private String countryName;
    private String continent;
    private String hotelCode;
    private String name;
    private Number latitude;
    private Number longitude;
    private String type;
}
