package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.v1_0_0.client.stakeholder.action.PassengerAction;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.PassengerSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerIP implements PassengerSV {

    @Autowired
    private PassengerAction passengerAction;

    @Override
    public void create(List<BPassengerRQ> passengers) {
        passengers.forEach(passenger -> passengerAction.create(passenger).subscribe());
    }

}
