package com.skybooking.skyhotelservice.v1_0_0.service.savedhotel;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelCodeRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface SavedHotelSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotels user saved
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS listing();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * user save or remove hotel
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCodeRQ
     * @return
     */
    StructureRS addOrUpdate(HotelCodeRQ hotelCodeRQ);

}
