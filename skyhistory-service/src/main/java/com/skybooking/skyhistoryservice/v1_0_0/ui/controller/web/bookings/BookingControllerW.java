package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingDetailSV;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.BookingDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/wv1.0.0")
public class BookingControllerW {


    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private BookingDetailSV bookingDetailSV;

    @Autowired
    private LocalizationBean localization;

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings")
    public ResRS bookings(HttpServletRequest request) {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking(request));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get booking details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/booking/{bookingCode}")
    public ResRS bookingDetail(OAuth2Authentication auth, @PathVariable String bookingCode) {

        if (auth.getOAuth2Request().getClientId().equals("skybooking-back-office")) {
            return localization.resAPI(HttpStatus.OK,"res_succ", bookingDetailSV.getBookingDetail(bookingCode, SendBookingNoAuthRQ.builder().isAdmin(true).build()));
        }

        return localization.resAPI(HttpStatus.OK,"res_succ", bookingDetailSV.getBookingDetail(bookingCode, null));
    }


}
