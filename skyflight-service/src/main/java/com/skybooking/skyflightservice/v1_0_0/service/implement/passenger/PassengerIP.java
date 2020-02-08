package com.skybooking.skyflightservice.v1_0_0.service.implement.passenger;

import com.skybooking.skyflightservice.v1_0_0.client.stakeholder.action.PassengerAction;
import com.skybooking.skyflightservice.v1_0_0.client.stakeholder.ui.request.PassengerSRQ;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerIP implements PassengerSV {

    @Autowired
    private PassengerAction passengerAction;

    @Override
    public void create(List<BookingPassengerRQ> passengers) {

        passengers.forEach(passenger -> {
            var passengerSRQ = new PassengerSRQ();

            passengerSRQ.setFirstName(passenger.getFirstName());
            passengerSRQ.setLastName(passenger.getLastName());
            passengerSRQ.setGender(passenger.getGender());
            passengerSRQ.setBirthDate(DatetimeFormat.parse("yyyy-MM-dd", passenger.getBirthDate()));
            passengerSRQ.setNationality(passenger.getNationality());
            passengerSRQ.setIdType(passenger.getIdType());
            passengerSRQ.setIdNumber(passenger.getIdNumber());
            passengerSRQ.setExpireDate(DatetimeFormat.parse("yyyy-MM-dd", passenger.getExpireDate()));

            passengerAction.create(passengerSRQ).subscribe();
        });
    }

}
