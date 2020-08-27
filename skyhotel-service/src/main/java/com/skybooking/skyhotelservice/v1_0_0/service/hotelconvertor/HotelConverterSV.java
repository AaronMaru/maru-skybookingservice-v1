package com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelConverterSV {

    List<HotelRS> availabilities(AvailabilityRSDS availabilityRSDS);

}
