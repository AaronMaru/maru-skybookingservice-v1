package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.ticketing.TicketRS;
import org.springframework.stereotype.Service;

@Service
public interface TicketSV {

    TicketRS issued(String bookingCode);

}
