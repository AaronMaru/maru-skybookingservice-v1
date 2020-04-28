package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingDataPaginationRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface BookingSV {

    BookingDataPaginationRS getBooking(HttpServletRequest request);

}
