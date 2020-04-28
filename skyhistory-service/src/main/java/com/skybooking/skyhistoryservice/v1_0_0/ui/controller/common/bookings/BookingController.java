package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.common.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.PrintRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by : maru
 * Date  : 4/18/2020
 * Time  : 3:23 PM
 */
@RestController
@RequestMapping(value = "v1.0.0")
public class BookingController {

    @Autowired
    private SendBookingSV sendBookingSV;

    @Autowired
    private LocalizationBean localization;
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("print")
    public ResRS printBookingPdf(@RequestParam(name = "bookingCode") String bookingCode) {
        PrintRS printRS = sendBookingSV.printBooking(bookingCode);
        return localization.resAPI(HttpStatus.OK,"is_skb_info", printRS);
    }
}
