package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.constant.CompanyConstant;
import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingCreateDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingPassengerDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.RevalidateFlight;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingRequestBody {

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private RevalidateFlight revalidateFlight;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set request body for request create PNR to supplier
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    public BookingCreateDRQ getBCreateDRQ(BookingCreateRQ request) {

        var requestId = request.getRequestId();
        var seats = 0;
        List<BookingSegmentDRQ> segments = new ArrayList<>();
        List<BookingPassengerDRQ> passengers = new ArrayList<>();

        var requestPassengers = request.getPassengers().stream().sorted(Comparator.comparing(BookingPassengerRQ::getBirthDate)).collect(Collectors.toList());

        for (BookingPassengerRQ passengerRQ: requestPassengers) {
            if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.ADULT) {
                seats++;
            } else if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.CHILD) {
                seats++;
            }

            BookingPassengerDRQ passenger = new BookingPassengerDRQ();
            passenger.setFirstName(passengerRQ.getFirstName());
            passenger.setLastName(passengerRQ.getLastName());
            passenger.setGender(passengerRQ.getGender());
            passenger.setBirthDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getBirthDate()));
            passenger.setIdType(passengerRQ.getIdType());
            passenger.setIdNumber(passengerRQ.getIdNumber());
            passenger.setExpireDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getExpireDate()));
            passenger.setNationality(passengerRQ.getNationality());
            passengers.add(passenger);
        }

        for (String leg: request.getLegIds()) {
            var legDetail = detailSV.getLegDetail(requestId, leg);

            for (LegSegmentDetail legSegment: legDetail.getSegments()) {
                segments.add(
                        revalidateFlight.setDSegment(
                                detailSV.getSegmentDetail(requestId, legSegment.getSegment()),
                                legSegment.getDateAdjustment(),
                                legSegment.getBookingCode(),
                                seats
                        )
                );
            }
        }

        BookingCreateDRQ requestBody = new BookingCreateDRQ();
        requestBody.setCompanyConstant(new CompanyConstant());
        requestBody.setContact(request.getContact());
        requestBody.setSegments(segments);
        requestBody.setPassengers(passengers);

        return requestBody;

    }

}
