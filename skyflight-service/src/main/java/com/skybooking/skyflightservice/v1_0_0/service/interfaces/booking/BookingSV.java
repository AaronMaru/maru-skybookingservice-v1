package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;

public interface BookingSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create Passenger Name Records (Create PNR)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param BCreateRQ
     * @param metadataTA
     * @return PNRCreateRS
     */
    PNRCreateRS create(BCreateRQ BCreateRQ, BookingMetadataTA metadataTA);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Cancel Passenger Name Records (Cancel PNR)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pnr
     * @return Boolean
     */
    Boolean cancel(String pnr);
}
