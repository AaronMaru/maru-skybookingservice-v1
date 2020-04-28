package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.common.report;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report.ReportSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1.0.0")
public class ReportController {

    @Autowired
    private ReportSV reportSV;

    @Autowired
    private LocalizationBean localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @request reportRQ
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-report")
    public ResRS getBookingReport(HttpServletRequest request) {

        return localization.resAPI(HttpStatus.OK,"res_succ", reportSV.getBookingReport(request));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get report listing
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @request reportRQ
     * @return ResponseEntity
     */
    @GetMapping(value = "/report-listing")
    public ResRS getReportListing(HttpServletRequest request) {

        return localization.resAPI(HttpStatus.OK,"res_succ", reportSV.getReportListing(request));

    }

}
