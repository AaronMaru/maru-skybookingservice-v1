package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/booking")
public class BookingController {

    @PostMapping("/create")
    public String create() {
        return "BOOKING CREATE PNR";
    }

    @PostMapping("/cancel")
    public String cancel() {
        return "BOOKING CANCEL PNR";
    }
}
