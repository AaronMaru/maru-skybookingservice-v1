package com.skybooking.skyhotelservice.v1_0_0.ui.controller.history;

import com.skybooking.skyhotelservice.v1_0_0.service.history.HistoryBookingSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/history-booking")
public class HistoryBookingController extends BaseController {

    @Autowired
    private HistoryBookingSV historyBookingSV;

    @GetMapping
    public ResponseEntity<StructureRS> listing() {
        return response(historyBookingSV.listing());
    }

    @GetMapping("/{bookingCode}")
    public ResponseEntity<StructureRS> detail(@PathVariable String bookingCode) { return response( new StructureRS(historyBookingSV.historyDetail(bookingCode))); }

}