package com.skybooking.backofficeservice.v1_0_0.service.hotel;

import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.HotelDetailRS;
import org.springframework.stereotype.Service;

@Service
public interface HotelSV {

    /**
     * Get hotel booking detail
     *
     * @param hotelBookingCode String;
     * @return HotelDetailRS
     */
    HotelDetailRS hotelDetail(String hotelBookingCode);
}
