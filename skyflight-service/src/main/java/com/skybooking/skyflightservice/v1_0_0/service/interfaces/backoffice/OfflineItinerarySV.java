package com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.CheckItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.OfflineItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface OfflineItinerarySV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check PNR have or not
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param checkItineraryRQ CheckItineraryRQ
     * @return StructureRS
     */
    StructureRS check(CheckItineraryRQ checkItineraryRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create booking offline
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraryRQ OfflineItineraryRQ
     * @return StructureRS
     */
    StructureRS create(OfflineItineraryRQ itineraryRQ);

}
