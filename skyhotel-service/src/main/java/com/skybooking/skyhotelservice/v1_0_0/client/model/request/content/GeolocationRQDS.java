package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeolocationRQDS {
    private double latitude;
    private double longitude;
    private int radius = 20;
}
