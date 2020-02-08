package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.BookingAction;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingDataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingIP extends BookingDataIP implements BookingSV {

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private BookingAction bookingAction;

    @Autowired
    private BookingDataSV bookingDataSV;

    @Autowired
    private BookingRequestBody bookingRequestBody;


    @Override
    public PNRCreateRS create(BookingCreateRQ request, BookingMetadataTA metadataTA) {

        var bookingCreated = new PNRCreateRS();

        /**
         * checking the shopping contain in cached.
         */
        if (!bookingUtility.isBookingCached(request.getRequestId())) {
            return bookingCreated;
        }

        /**
         * action booking request
         */
        var pnrRS = bookingAction.create(bookingRequestBody.getBCreateDRQ(request));
        System.out.println(pnrRS);
        var status = pnrRS.get("CreatePassengerNameRecordRS").get("ApplicationResults").get("status").textValue();

        if (status.equals("Complete")) {
            var itineraryID = pnrRS.get("CreatePassengerNameRecordRS").get("ItineraryRef").get("ID").textValue();
            var requestTA = new BookingRequestTA();

            requestTA.setRequest(request);
            requestTA.setItineraryCode(itineraryID);

            metadataTA = bookingUtility.getBookingMetadata(requestTA, metadataTA);

            /**
             * store booking records
             */
            try {
                var booking = bookingDataSV.save(requestTA, metadataTA, pnrRS, request);

                bookingCreated.setBookingCode(booking.getBookingCode());
                bookingCreated.setBookingRef(booking.getItineraryRef());
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return bookingCreated;
    }



    @Override
    public Boolean cancel(String pnr) {
        return null;
    }

}
