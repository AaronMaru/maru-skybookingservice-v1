package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.constant.UserConstant;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.MetadataSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "MetadataIP")
public class MetadataIP implements MetadataSV {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;


    @Override
    public BookingMetadataTA getSkyownerMetadata() {

        var header = headerBean.getHeaders();
        var bookingMetadata = this.getMetadata();
        bookingMetadata.getUser().setCompanyId(header.getCompanyId());

        return bookingMetadata;

    }


    @Override
    public BookingMetadataTA getSkyuserMetadata() {

        var bookingMetadata = this.getMetadata();
        bookingMetadata.getUser().setUserType("skyuser");

        return bookingMetadata;
    }


    private BookingMetadataTA getMetadata() {

        var header = headerBean.getHeaders();
        var bookingMetadata = new BookingMetadataTA();

        bookingMetadata.setUser(this.getUserAuthenticationMetadata());
        bookingMetadata.setCurrencyLocaleCode(header.getCurrencyCode());

        return bookingMetadata;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get user authentication's information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return UserAuthenticationMetaTA
     */
    @Override
    public UserAuthenticationMetaTA getUserAuthenticationMetadata() {

        var header = headerBean.getHeaders();
        var userType = jwtUtils.getClaim("userType", String.class) == null ? "anonymous" : jwtUtils.getClaim("userType", String.class);

        var metadata = new UserAuthenticationMetaTA();
        metadata.setUserType(userType);
        metadata.setUserId(jwtUtils.getClaim("userId", Integer.class));
        metadata.setStakeholderId(jwtUtils.getClaim("stakeholderId", Integer.class));

        if (UserConstant.SKYOWNER.equalsIgnoreCase(userType)) {
            if (header.getCompanyId() == null) {
                metadata.setUserType(UserConstant.SKYUSER);
            }
        }

        metadata.setCompanyId(header.getCompanyId());

        if (!userType.equalsIgnoreCase("anonymous")) {
            metadata.setAuthenticated(true);
        }

        return metadata;
    }
}
