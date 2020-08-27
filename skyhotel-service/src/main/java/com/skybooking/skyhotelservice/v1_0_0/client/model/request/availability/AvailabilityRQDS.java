package com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AvailabilityRQDS {

    private Stay stay;

    private List<Occupancy> occupancies;

    private Destination destination;

    private Geolocation geolocation;

    private List<Review> reviews = new ArrayList<>();

    private Keyword keywords;

}
