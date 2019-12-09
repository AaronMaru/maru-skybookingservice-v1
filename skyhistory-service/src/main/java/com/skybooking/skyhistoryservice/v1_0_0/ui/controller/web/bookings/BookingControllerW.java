package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wv1.0.0")
public class BookingControllerW {


    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/bookings-company")
    public ResRS bookingsCompany() {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking("company"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings-skyuser")
    public ResRS bookingsSkyuser() {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking("skyuser"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings-staff")
    public ResRS bookingsStaff() {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking("staff"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get booking details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/booking/{id}")
    public ResRS getBookingDetail(@PathVariable Long id) {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBookingDetail(id));
    }


}
