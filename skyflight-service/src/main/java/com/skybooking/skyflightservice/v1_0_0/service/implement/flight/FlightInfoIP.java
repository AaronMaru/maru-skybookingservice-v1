package com.skybooking.skyflightservice.v1_0_0.service.implement.flight;

import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight.FlightInfoSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightInfoIP implements FlightInfoSV {

    @Autowired
    private FlightInfoNQ flightInfoNQ;

    @Override
    public List<FlightInfoTO> getFlightInfoEnabled() {
        return flightInfoNQ.getFlightInfoShoppingEnabled();
    }
}
