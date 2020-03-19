package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.app.report;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report.ReportSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/mv1.0.0")
public class ReportControllerM {

    @Autowired
    private ReportSV reportSV;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LocalizationBean localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-report")
    public ResRS getBookingReport(@RequestParam(name = "classType", defaultValue = "ALL") String classType,
                                  @RequestParam(name = "tripType", defaultValue = "ALL") String tripType,
                                  @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}")
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                  @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}")
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        var response = reportSV.getBookingReport(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), /* JwtUtils.getClaim("userRole", String.class)*/ "companyConstant", classType, tripType, startDate.toString(), endDate.toString());

        return localization.resAPI(HttpStatus.OK,"res_succ", response);

    }

}
