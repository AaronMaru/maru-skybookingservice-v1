package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerSV {
    void create(List<BPassengerRQ> passengers);
}
