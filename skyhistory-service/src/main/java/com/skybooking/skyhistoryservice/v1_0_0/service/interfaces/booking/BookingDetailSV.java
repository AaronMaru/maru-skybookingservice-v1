package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.BookingDetailRS;
import org.springframework.stereotype.Service;

@Service
public interface BookingDetailSV {

    BookingDetailRS getBookingDetail(Long id);

}
