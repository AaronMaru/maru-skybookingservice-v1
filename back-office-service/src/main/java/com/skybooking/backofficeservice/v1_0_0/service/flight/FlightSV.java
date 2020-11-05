package com.skybooking.backofficeservice.v1_0_0.service.flight;

import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;

public interface FlightSV {

    /**
     * Get flight booking detail
     *
     * @param bookingCode String;
     * @return StructureRS
     */
    StructureRS flightDetail(String bookingCode);
}
