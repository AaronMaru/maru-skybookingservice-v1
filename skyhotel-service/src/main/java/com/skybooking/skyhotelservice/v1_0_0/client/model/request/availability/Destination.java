package com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability;

import lombok.Data;

@Data
public class Destination {
    private String destinationCode;
    private String hotelCode;

    public Destination() { }

    public Destination(String destinationCode) {
        this.destinationCode = destinationCode;
    }
}
