package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import com.skybooking.skyflightservice.constant.CompanyConstant;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingContactRQ;
import lombok.Data;

import java.util.List;

@Data
public class BookingCreateDRQ {

    private CompanyConstant companyConstant;
    private BookingContactRQ contact;
    private List<BookingPassengerDRQ> passengers;
    private List<BookingSegmentDRQ> segments;

}
