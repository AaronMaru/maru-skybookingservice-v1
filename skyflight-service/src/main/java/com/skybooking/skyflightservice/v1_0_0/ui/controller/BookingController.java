package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.config.response.MessageRS;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.PassengerSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingFailRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/booking")
public class BookingController {

    @Autowired
    private PassengerSV passengerSV;

    @Autowired
    private ShoppingSV shoppingSV;

    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private Localization locale;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Booking or Create PNR (Create Passenger Name Records)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody BCreateRQ request,
                                 @RequestHeader("company") Integer company,
                                 @RequestHeader("currency") String currency) {

        /**
         * Pre-create passenger
         */
        //  passengerSV.create(request.getPassengers());

        /**
         * Revalidate flight shopping before create PNR
         * 1. check price
         * 2. check seats available
         */
//        if (!shoppingSV.revalidate(request)) {
//            return new ResponseEntity<>(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY, locale.multiLanguageRes(MessageRS.BOOKING_FAIL), null), HttpStatus.FAILED_DEPENDENCY);
//        }

        var bookingMetadata = new BookingMetadataTA();
        var userType = jwtUtils.getClaim("userType", String.class);
        bookingMetadata.setUserType(userType);
        bookingMetadata.setUserId(jwtUtils.getClaim("userId", Integer.class));
        bookingMetadata.setStakeholderId(jwtUtils.getClaim("stakeholderId", Integer.class));
        bookingMetadata.setCompanyId(company);

        if (currency != null) {
            bookingMetadata.setCurrencyLocaleCode(currency);
        }

        /**
         * Create PNR
         * 1. request create PNR to supplier
         * 2. save PNR info into DB
         */
        PNRCreateRS pnr = bookingSV.create(request, bookingMetadata);
        if (pnr.getBookingRef().equals("")) {
            return new ResponseEntity<>(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY, locale.multiLanguageRes(MessageRS.BOOKING_FAIL), null), HttpStatus.FAILED_DEPENDENCY);
        }

        return new ResponseEntity<>(new BookingRS(HttpStatus.OK, locale.multiLanguageRes(MessageRS.BOOKING_SUCCESS), pnr), HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public String cancel() {
        return "BOOKING CANCEL PNR";
    }

}
