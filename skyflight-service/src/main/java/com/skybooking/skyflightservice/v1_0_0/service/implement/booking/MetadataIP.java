package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.MetadataSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataIP implements MetadataSV {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;


    @Override
    public BookingMetadataTA getMetadata() {

        var header = headerBean.getHeaders();
        var bookingMetadata = new BookingMetadataTA();
        var userType = jwtUtils.getClaim("userType", String.class);

        bookingMetadata.setUserType(userType);
        bookingMetadata.setUserId(jwtUtils.getClaim("userId", Integer.class));
        bookingMetadata.setStakeholderId(jwtUtils.getClaim("stakeholderId", Integer.class));
        bookingMetadata.setCompanyId(header.getCompanyId());
        bookingMetadata.setCurrencyCode(header.getCurrencyCode());

        return bookingMetadata;

    }
}
