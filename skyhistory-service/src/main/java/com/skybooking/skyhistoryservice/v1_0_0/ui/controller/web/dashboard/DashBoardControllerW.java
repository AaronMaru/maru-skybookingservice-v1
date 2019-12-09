package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.dashboard;


import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard.DashboardSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/wv1.0.0")
public class DashBoardControllerW {

    @Autowired
    private DashboardSV dashboardSV;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private Localization localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get recent booking by company's id and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/recent-booking")
    public ResRS getRecentBooking() {

        var responses = dashboardSV.getRecentBooking(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), jwtUtils.getClaim("userRole", String.class), 5);

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking progress summary by company's id and user's id with filter option
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter    options: range, daily, monthly, yearly
     * @param startDate required for filter as range and default value is current date
     * @param endDate   required for filter as range and default value is current date
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-progress")
    public ResRS getBookingProgress(@RequestParam(name = "filter", defaultValue = "weekly") String filter,
                                             @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                             @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        var responses = dashboardSV.getBookingProgress(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), jwtUtils.getClaim("userRole", String.class), filter, startDate.toString(), endDate.toString());

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking timeline summary by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter options: daily, weekly, monthly
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-calendar")
    public ResRS getBookingTimeline(@RequestParam(name = "filter") String filter) {

        var responses = dashboardSV.getBookingTimeline(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), jwtUtils.getClaim("userRole", String.class), filter);

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get top sellers report by company's id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter    options: range, daily, weekly, monthly, yearly
     * @param startDate required for filter as range and default value is current date
     * @param endDate   required for filter as range and default value is current date
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-top-seller")
    public ResRS getBookingTopSeller(@RequestParam(name = "filter", defaultValue = "weekly") String filter,
                                              @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        var response = dashboardSV.getBookingTopSeller(jwtUtils.getClaim("companyId", Long.class), filter, startDate.toString(), endDate.toString(), 5);

        return localization.resAPI(HttpStatus.OK,"res_succ", response);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking activity logging by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @param startDate
     * @param endDate
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-activity")
    public ResRS getBookingActivity(@RequestParam(name = "filter", defaultValue = "weekly") String filter,
                                             @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                             @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        var responses = dashboardSV.getBookingActivity(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), jwtUtils.getClaim("userRole", String.class), filter, startDate.toString(), endDate.toString(), 5, headerBean.getLocalizationId());

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


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
    public ResRS getBookingReport(@RequestParam(name = "classType", defaultValue = "allclass") String classType,
                                           @RequestParam(name = "tripType", defaultValue = "alltrip") String tripType,
                                           @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                           @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        var response = dashboardSV.getBookingReport(jwtUtils.getClaim("companyId", Long.class), jwtUtils.getClaim("stakeholderId", Long.class), jwtUtils.getClaim("userType", String.class), /* JwtUtils.getClaim("userRole", String.class)*/ "company", classType, tripType, startDate.toString(), endDate.toString());

        return localization.resAPI(HttpStatus.OK,"res_succ", response);

    }
}
