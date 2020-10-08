package com.skybooking.skyhotelservice.v1_0_0.ui.controller.checkrate;

import com.skybooking.skyhotelservice.v1_0_0.service.booking.BookingSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.CheckRateRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.ReserveRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0")
public class BookingController extends BaseController {

    @Autowired
    private BookingSV bookingSV;

    @PostMapping("/checkrates")
    public ResponseEntity<StructureRS> checkRate(@RequestBody CheckRateRQ checkRate) {
        return response(bookingSV.checkRate(checkRate));
    }

    @PostMapping("/reserve")
    public ResponseEntity<StructureRS> reserve(@RequestBody ReserveRQ reserveRQ) {
        return response(bookingSV.reserve(reserveRQ));
    }

}
