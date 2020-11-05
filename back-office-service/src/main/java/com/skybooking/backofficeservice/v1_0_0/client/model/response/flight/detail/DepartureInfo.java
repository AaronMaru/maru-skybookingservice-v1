package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail;

import lombok.Data;

@Data
public class DepartureInfo {
    private String country;
    private String city;
    private String locationCode;
    private String airportName;
    private String date;
}
