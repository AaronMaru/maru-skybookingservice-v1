package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.constant.CompanyConstant;
import com.skybooking.skyflightservice.constant.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.*;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.RevalidateFlight;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
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

    @Autowired
    private TransformSV transformSV;

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

        for (BookingPassengerRQ passengerRQ : requestPassengers) {
            if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.ADULT) {
                seats++;
            } else if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.CHILD) {
                seats++;
            }

            BookingPassengerDRQ passenger = new BookingPassengerDRQ();
            passenger.setFirstName(passengerRQ.getFirstName());
            passenger.setLastName(passengerRQ.getLastName());
            passenger.setBirthDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getBirthDate()));
            passenger.setGender(passengerRQ.getGender());
            passenger.setIdType(passengerRQ.getIdType());
            passenger.setIdNumber(passengerRQ.getIdNumber());
            passenger.setExpireDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getExpireDate()));
            passenger.setNationality(passengerRQ.getNationality());
            passengers.add(passenger);
        }

        var classOfServiceId = requestId;
        var fareBasisId = requestId;
        for (String leg : request.getLegIds()) {
            classOfServiceId = classOfServiceId + leg;
            fareBasisId = fareBasisId + leg;
        }
        List<String> classOfServices = transformSV.getNewClassOfService(classOfServiceId);
        List<String> fareBasisCached = transformSV.getFareBasis(fareBasisId + "-fare-basis");
        List<BookingFareComponentRQ> fareComponentRQs = new ArrayList<>();

        for (String fareBasisCode: fareBasisCached) {
            BookingFareComponentRQ fareComponentRQ = new BookingFareComponentRQ();
            fareComponentRQ.setFareBasis(new FareBasisRQ(fareBasisCode));
            fareComponentRQs.add(fareComponentRQ);
        }

        var indexClassOfServices = 0;
        for (String leg : request.getLegIds()) {
            var legDetail = detailSV.getLegDetail(requestId, leg);

            for (LegSegmentDetail legSegment : legDetail.getSegments()) {

                var classOfService = legSegment.getBookingCode();
                if (detailSV.getShoppingDetail(requestId).getSegments().size() == classOfServices.size()) {
                    classOfService = classOfServices.get(indexClassOfServices);
                }

                segments.add(
                    revalidateFlight.setDSegment(
                        detailSV.getSegmentDetail(requestId, legSegment.getSegment()),
                        legSegment.getDateAdjustment() + legSegment.getPreviousDateAdjustment(),
                        classOfService,
                        seats
                    )
                );

                indexClassOfServices++;
            }
        }

        BookingCreateDRQ requestBody = new BookingCreateDRQ();
        requestBody.setCompanyConstant(new CompanyConstant());
        requestBody.setContact(request.getContact());
        requestBody.setSegments(segments);
        requestBody.setPassengers(passengers);
        requestBody.setFareComponents(fareComponentRQs);

        return requestBody;

    }

}
