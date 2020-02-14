package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentSucceedRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentSucceedRS;
import org.springframework.stereotype.Service;

@Service
public interface BookingSV extends BookingDataSV, MetadataSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create Passenger Name Records (Create PNR)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param BookingCreateRQ
     * @param metadataTA
     * @return PNRCreateRS
     */
    PNRCreateRS create(BookingCreateRQ BookingCreateRQ, BookingMetadataTA metadataTA);



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
