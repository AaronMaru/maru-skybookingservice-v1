package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.app.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mv1.0.0")
public class BookingControllerM {


    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings companyConstant
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/bookings-company")
    public ResRS bookingsCompany() {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking("companyConstant"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/bookings-skyuser")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResRS bookingsSkyuser() {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBooking("skyuser"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
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
     * @Return ResponseEntity
     */
    @GetMapping("/booking/{id}")
    public ResRS getBookingDetail(@PathVariable Long id) {
        return localization.resAPI(HttpStatus.OK,"res_succ", bookingSV.getBookingDetail(id));
    }


}
