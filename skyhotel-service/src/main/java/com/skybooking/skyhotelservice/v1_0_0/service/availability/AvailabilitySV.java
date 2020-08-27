package com.skybooking.skyhotelservice.v1_0_0.service.availability;

import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.AvailabilityRQDS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelDetailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface AvailabilitySV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * search hotel availabilities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityRQ
     * @param paramsRQ
     * @return
     */
    StructureRS availability(AvailabilityRQ availabilityRQ, Map<String, Object> paramsRQ);

    /**
     * Get availability by destination codes
     *
     * @param destinationCode
     * @return
     */
    List<HotelRS> getAvailabilityByDesCodes(List<String> destinationCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate sample hotel availability DS request
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCodes
     * @return
     */
    AvailabilityRQDS getSampleRequest(List<Integer> hotelCodes);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelDetailRQ
     * @return
     */
    StructureRS detail(HotelDetailRQ hotelDetailRQ);

}
