package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.AvailabilityHotelRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.ContentRSDS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseHotelRSDS {

    private ResourceRSDS resource;
    private List<ContentRSDS> hotelContents = new ArrayList<>();
    private List<AvailabilityHotelRSDS> availabilities = new ArrayList<>();

}
