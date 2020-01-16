package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingDataPaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingEmailDetailRS;
import org.springframework.stereotype.Service;

@Service
public interface BookingSV {

    BookingDataPaginationRS getBooking(String stake);
    BookingDetailRS getBookingDetail(Long id);
    BookingEmailDetailRS getBookingDetailEmail(Long id);

}
