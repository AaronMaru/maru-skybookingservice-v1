package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.destination;

import lombok.Data;

@Data
public class DestinationRS {
    private String destinationCode;
    private String countryName;
    private String continent;
    private String hotelCode;
    private String name;
    private Number latitude;
    private Number longitude;
    private String type;
    private String typeGroup;
    private String searchBy;
}
