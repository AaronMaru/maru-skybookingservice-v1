package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.constant.MessageConstant;
import com.skybooking.skyflightservice.constant.RevalidateConstant;
import com.skybooking.skyflightservice.exception.httpstatus.BadRequestRS;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.header.SkyownerHeaderSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCancelRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingFailRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.BookingRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.skybooking.skyflightservice.constant.MessageConstant.CANCEL_SUCCESS;

@RestController
@RequestMapping("v1.0.0/booking")
public class BookingController {

    @Autowired
    private SkyownerHeaderSV skyownerHeaderSV;

    @Autowired
    private PassengerSV passengerSV;

    @Autowired
    private ShoppingSV shoppingSV;

    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private Localization locale;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private ActivityLoggingBean activityLog;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Booking or Create PNR (Create Passenger Name Records) FOR SKYOWNER
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param headers
     * @param request
     * @return
     */
    @PostMapping("/skyowner/create")
    public ResponseEntity create(@RequestHeader HttpHeaders headers, @Valid @RequestBody BookingCreateRQ request) {

        /**
         * Check header
         */
        if (!skyownerHeaderSV.check(headers)) {
            return new ResponseEntity<>(
                new BadRequestRS(locale.multiLanguageRes(MessageConstant.BAD_REQUEST),
                    headerBean.getSkyownerHeaderMissing()),
                HttpStatus.BAD_REQUEST);
        }

        /**
         * Pre-create passenger
         */
        try {
            passengerSV.create(request.getPassengers());
        } catch (Exception ex) {
        }

        /**
         * Revalidate flight shopping before create PNR 1. check price 2. check seats
         * available
         */
        var revalidate = shoppingSV.revalidate(request);
        if (revalidate.getStatus() != RevalidateConstant.SUCCESS) {
            return new ResponseEntity<>(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY,
                locale.multiLanguageRes(revalidate.getMessage()), new PNRCreateRS()),
                HttpStatus.FAILED_DEPENDENCY);
        }

        /**
         * Create PNR 1. request create PNR to supplier 2. save PNR info into DB
         */
        PNRCreateRS pnr = bookingSV.create(request, bookingSV.getSkyownerMetadata());
        if (pnr.getBookingCode().isEmpty()) {
            return new ResponseEntity<>(
                new BookingFailRS(HttpStatus.FAILED_DEPENDENCY,
                    locale.multiLanguageRes(MessageConstant.BOOKING_FAIL), pnr),
                HttpStatus.FAILED_DEPENDENCY);
        }

        activityLog.activities(ActivityLoggingBean.Action.INDEX_BOOKINNG, activityLog.getUser(),
            bookingRP.getBookingByBookingCode(pnr.getBookingCode()));

        return new ResponseEntity<>(new BookingRS(HttpStatus.OK,
            locale.multiLanguageRes(MessageConstant.BOOKING_SUCCESS), pnr), HttpStatus.OK);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Booking or Create PNR (Create Passenger Name Records) FOR SKYUSER
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @PostMapping("/skyuser/create")
    public ResponseEntity create(@Valid @RequestBody BookingCreateRQ request) {

        /**
         * Pre-create passenger
         */

        try {
            passengerSV.create(request.getPassengers());
        } catch (Exception ex) {
        }

        /**
         * Revalidate flight shopping before create PNR 1. check price 2. check seats
         * available
         */
        var revalidate = shoppingSV.revalidate(request);
        if (revalidate.getStatus() != RevalidateConstant.SUCCESS) {
            return new ResponseEntity<>(new BookingFailRS(HttpStatus.FAILED_DEPENDENCY,
                locale.multiLanguageRes(revalidate.getMessage()), new PNRCreateRS()),
                HttpStatus.FAILED_DEPENDENCY);
        }

        /**
         * Create PNR 1. request create PNR to supplier 2. save PNR info into DB
         */
        PNRCreateRS pnr = bookingSV.create(request, bookingSV.getSkyuserMetadata());
        if (pnr.getBookingCode().equals("")) {
            return new ResponseEntity<>(
                new BookingFailRS(HttpStatus.FAILED_DEPENDENCY,
                    locale.multiLanguageRes(MessageConstant.BOOKING_FAIL), pnr),
                HttpStatus.FAILED_DEPENDENCY);
        }

        activityLog.activities(ActivityLoggingBean.Action.INDEX_BOOKINNG, activityLog.getUser(),
            bookingRP.getBookingByBookingCode(pnr.getBookingCode()));

        return new ResponseEntity<>(new BookingRS(HttpStatus.OK,
            locale.multiLanguageRes(MessageConstant.BOOKING_SUCCESS), pnr), HttpStatus.OK);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyuser cancel booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCancelRQ
     * @return
     */
    @PostMapping("/skyuser/cancel")
    public ResponseEntity cancel(@Valid @RequestBody BookingCancelRQ bookingCancelRQ) {

        bookingSV.cancel(bookingCancelRQ.getBookingCode());

        return new ResponseEntity<>(new BookingRS(HttpStatus.OK, locale.multiLanguageRes(CANCEL_SUCCESS), null),
            HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Skyowner cancel booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param headers
     * @param bookingCancelRQ
     * @return
     */
    @PostMapping("/skyowner/cancel")
    public ResponseEntity cancel(@RequestHeader HttpHeaders headers, @Valid @RequestBody BookingCancelRQ bookingCancelRQ) {
        if (!skyownerHeaderSV.check(headers)) {
            return new ResponseEntity<>(
                new BadRequestRS(locale.multiLanguageRes(MessageConstant.BAD_REQUEST),
                    headerBean.getSkyownerHeaderMissing()),
                HttpStatus.BAD_REQUEST);
        }

        bookingSV.cancel(bookingCancelRQ.getBookingCode());

        return new ResponseEntity<>(new BookingRS(HttpStatus.OK, locale.multiLanguageRes(CANCEL_SUCCESS), null),
            HttpStatus.OK);
    }

}
