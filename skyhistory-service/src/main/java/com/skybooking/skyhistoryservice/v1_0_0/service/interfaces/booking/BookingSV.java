package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingDataPaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingEmailDetailRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface BookingSV {

    BookingDataPaginationRS getBooking(HttpServletRequest request);
    BookingDetailRS getBookingDetail(Long id);
    BookingEmailDetailRS getBookingDetailEmail(Long id);
    BookingEmailDetailRS getBookingDetailEmailWithoutAuth(Long id, SendBookingNoAuthRQ sendBookingNoAuthRQ);

}
