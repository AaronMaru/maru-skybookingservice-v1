package com.skybooking.backofficeservice.v1_0_0.service.flight;

import com.skybooking.backofficeservice.v1_0_0.client.action.flight.FlightAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.FlightDetailServiceRS;
import com.skybooking.backofficeservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.FlightDetailRS;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightIP extends BaseServiceIP implements FlightSV{

    private final ModelMapper modelMapper;

    @Autowired
    private FlightAction flightAction;

    @Override
    public StructureRS flightDetail(String bookingCode)
    {
        FlightDetailServiceRS flightDetailServiceRS =  flightAction.getFlightDetail(bookingCode).getData();
        FlightDetailRS flightDetailRS = modelMapper.map(flightDetailServiceRS, FlightDetailRS.class);

        return responseBodyWithSuccessMessage(flightDetailRS);
    }
}
