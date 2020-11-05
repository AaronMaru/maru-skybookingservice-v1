package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.flight.FlightSV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/flight")
public class FlightController extends  BaseController{

    @Autowired
    private FlightSV flightSV;

    @GetMapping("/detail/{bookingCode}")
    public ResponseEntity<StructureRS> flightDetail(@PathVariable String bookingCode){
        return response(flightSV.flightDetail(bookingCode));
    }
}
