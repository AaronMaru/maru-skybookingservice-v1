package com.skybooking.skyhotelservice.v1_0_0.client.model.request.hotel;

import com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability.Destination;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability.Geolocation;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.ReviewRQDS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataHotelRQDS {

    private HotelRQDS hotels;
    private List<Destination> destinations;
    private Geolocation geolocation;
    private List<ReviewRQDS> reviews = new ArrayList<>();

    public DataHotelRQDS(HotelRQDS hotels) {
        this.hotels = hotels;
        this.reviews.add(new ReviewRQDS());
    }

    public DataHotelRQDS(List<Destination> destinations) {
        this.destinations = destinations;
        this.reviews.add(new ReviewRQDS());
    }

    public DataHotelRQDS(Geolocation geolocation) {
        this.geolocation = geolocation;
        this.reviews.add(new ReviewRQDS());
    }
}
