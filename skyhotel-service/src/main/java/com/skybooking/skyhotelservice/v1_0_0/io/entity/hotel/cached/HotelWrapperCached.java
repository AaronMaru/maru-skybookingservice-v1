package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class HotelWrapperCached implements Serializable {

    private String id;
    private AvailabilityRQ availabilityRQ;
    private ResourceCached resource;
    private String locale;
    private List<HotelCached> hotelList = new ArrayList<>();
    private FilterIndexCached filterIndex;

}
