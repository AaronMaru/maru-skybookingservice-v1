package com.skybooking.skyhotelservice.v1_0_0.service.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface RecentSearchSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve recent search of user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS listing();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete recent search
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS delete();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * save when user search hotel
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityRQ
     */
    void saveOrUpdate(AvailabilityRQ availabilityRQ);

}
