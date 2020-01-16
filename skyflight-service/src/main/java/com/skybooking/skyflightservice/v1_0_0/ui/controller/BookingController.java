package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.PassengerSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.transformer.observer.BookingOS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingCreateRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingFailRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/booking")
public class BookingController {

    @Autowired
    private PassengerSV passengerSV;

    @Autowired
    private ShoppingSV shoppingSV;

    @Autowired
    private BookingSV bookingSV;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody BCreateRQ request) {

        passengerSV.create(request.getPassengers());

        BookingOS priced = shoppingSV.revalidate(request.getFlightId());
        if (!priced.getStatus()) {
            return new ResponseEntity(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY, "", null), HttpStatus.FAILED_DEPENDENCY);
        }

        BookingOS seats = shoppingSV.checkSeats();
        if (!seats.getStatus()) {
            return new ResponseEntity(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY, "", null), HttpStatus.FAILED_DEPENDENCY);
        }

        return new ResponseEntity(new BookingCreateRS(HttpStatus.OK, "", bookingSV.create(request).getResult()), HttpStatus.OK);
    }

    @PostMapping("/hello")
    public Object test(@Valid @RequestBody BCreateRQ request) {
        return null;
    }

    @PostMapping("/cancel")
    public String cancel() {
        return "BOOKING CANCEL PNR";
    }

}
