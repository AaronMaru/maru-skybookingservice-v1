package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wv1.0.0")
public class BookingControllerW {


    @Autowired
    private BookingSV bookingSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings-company")
    public ResponseEntity bookingsCompany() {
        return new ResponseEntity(bookingSV.getBooking("company"), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings-skyuser")
    public ResponseEntity bookingsSkyuser() {
        return new ResponseEntity(bookingSV.getBooking("skyuser"), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bookings staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/bookings-staff")
    public ResponseEntity bookingsStaff() {
        return new ResponseEntity(bookingSV.getBooking("staff"), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get booking details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping("/booking/{id}")
    public ResponseEntity getBookingDetail(@PathVariable Long id) {
        return new ResponseEntity(bookingSV.getBookingDetail(id), HttpStatus.OK);
    }


}
