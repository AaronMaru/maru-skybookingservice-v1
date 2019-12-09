package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/ticketing")
public class TicketingController {

    @PostMapping("/issued")
    public String create() {
        return "TICKETING ISSUED AIR TICKET";
    }

    @PostMapping("/info")
    public String getTicketInfo() {
        return "TICKETING GET TICKET INFO";
    }
}
