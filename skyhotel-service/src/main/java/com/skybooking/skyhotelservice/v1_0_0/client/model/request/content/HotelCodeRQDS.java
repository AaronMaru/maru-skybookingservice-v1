package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import lombok.Data;

import java.util.List;

@Data
public class HotelCodeRQDS {

    private List<DestinationRQDS> destinations;
    private GeolocationRQDS geolocation;
    private String accommodationType = "";

    public HotelCodeRQDS() { }

    public HotelCodeRQDS(List<DestinationRQDS> destinations) {
        this.destinations = destinations;
    }

    public HotelCodeRQDS(List<DestinationRQDS> destinations, GeolocationRQDS geolocation) {
        this.destinations = destinations;
        this.geolocation = geolocation;
    }

    public HotelCodeRQDS(List<DestinationRQDS> destinations, GeolocationRQDS geolocation, String accommodationType) {
        this.destinations = destinations;
        this.geolocation = geolocation;
        this.accommodationType = accommodationType;
    }
}
