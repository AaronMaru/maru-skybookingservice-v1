package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
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


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * user authentication information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return userAuthenticationMeta
     */
    UserAuthenticationMetaTA getUserAuthenticationMetadata();

}
