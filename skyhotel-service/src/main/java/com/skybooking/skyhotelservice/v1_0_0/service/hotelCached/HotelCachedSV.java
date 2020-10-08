package com.skybooking.skyhotelservice.v1_0_0.service.hotelCached;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelWrapperCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HotelCachedSV {

    void saveCacheHotelWrapperAsync(HotelWrapperCached hotelWrapperCached);

    void saveCacheHotelWrapper(HotelWrapperCached hotelWrapperCached);

    HotelWrapperCached getHotelWrapperCached(HotelWrapperRS<HotelRS> source, ResourceRSDS resource);

    HotelWrapperRS<HotelRS> getHotelWrapper(HotelWrapperCached hotelWrapperCached);

    HotelWrapperRS<HotelRS> retrieveHotelWrapper(String id);

    List<HotelRS> getHotelList(List<HotelCached> hotelCached, AvailabilityRQ availabilityRQ);

    List<HotelRS> getHotelList(List<HotelCached> hotelCached, String sortBy, FilterRQ filterRQ);

    List<HotelRS> retrieveHotelList(String id);

    List<HotelRS> retrieveHotelList(String id, String sortBy, FilterRQ filterRQ);

    List<HotelRS> retrieveHotelList(String id, AvailabilityRQ availabilityRQ);

    List<HotelCached> retrieveHotelListCached(String id);

    Optional<HotelRS> retrieveHotel(String id, Integer hotelCode);

}
