package com.skybooking.skyhotelservice.v1_0_0.service.hotelCached;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelCachedSV {

    void saveHotelList(String id, HotelWrapperRS<HotelRS> source, ResourceRSDS resource);

    HotelWrapperRS<HotelRS> retrieveHotelWrapper(String id);

    List<HotelRS> retrieveHotelList(String id);

    List<HotelRS> retrieveHotelList(String id, String sortBy, FilterRQ filterRQ);

    List<HotelCached> retrieveHotelListCached(String id);

}
