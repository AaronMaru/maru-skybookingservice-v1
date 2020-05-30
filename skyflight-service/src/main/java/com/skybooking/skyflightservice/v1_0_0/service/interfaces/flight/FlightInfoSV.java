package com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight;

import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightInfoSV {

    List<FlightInfoTO> getFlightInfoEnabled();
}
