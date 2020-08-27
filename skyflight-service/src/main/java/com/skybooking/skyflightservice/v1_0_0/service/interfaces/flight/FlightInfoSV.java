package com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight;

import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import com.skybooking.skyflightservice.v1_0_0.service.model.ShareFlightRS;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.ShareFlightRQ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightInfoSV {

    List<FlightInfoTO> getFlightInfoEnabled();

    ShareFlightRS sharingFlight(ShareFlightRQ shareFlightRQ, UserAuthenticationMetaTA metadata);
}
