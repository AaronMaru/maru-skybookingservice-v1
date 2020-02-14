package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import org.springframework.stereotype.Service;

@Service
public interface MetadataSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get metadata
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    BookingMetadataTA getSkyownerMetadata();



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get metadata
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    BookingMetadataTA getSkyuserMetadata();

}
