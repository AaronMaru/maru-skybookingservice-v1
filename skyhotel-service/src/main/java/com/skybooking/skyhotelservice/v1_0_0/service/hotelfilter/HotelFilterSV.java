package com.skybooking.skyhotelservice.v1_0_0.service.hotelfilter;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter.FilterRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelFilterSV {

    FilterRS available(List<HotelRS> hotelCachedList, FilterRQ filterRQ);

    List<HotelRS> filter(List<HotelRS> hotelCachedList, FilterRQ filter);

    List<HotelRS> filterByOccupancy(List<HotelRS> hotelRS, AvailabilityRQ availabilityRQ);

}
