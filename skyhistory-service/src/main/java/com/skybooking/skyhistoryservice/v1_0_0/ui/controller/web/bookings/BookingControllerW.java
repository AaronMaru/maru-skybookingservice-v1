package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingDetailSV;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/booking/{id}")
    public ResRS bookingDetail(@PathVariable Long id) {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingDetailSV.getBookingDetail(id));
    }


}
