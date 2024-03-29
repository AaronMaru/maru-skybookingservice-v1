package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.common.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard.DashboardSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/v1.0.0")
public class DashboardController {

    @Autowired
    private DashboardSV dashboardSV;

    @Autowired
    private HeaderBean headerBean;


    @Autowired
    private LocalizationBean localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get recent booking by company id and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping(value = "/recent-booking")
    public ResRS getRecentBooking() {

        var responses = dashboardSV.getRecentBooking(5);

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get booking progress summary by company id and user's id with filter option
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

        var responses = dashboardSV.getBookingProgress(filter.toLowerCase(), startDate.toString(), endDate.toString());

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get booking timeline summary by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter options: daily, weekly
     * @return ResponseEntity
     */
    @GetMapping(value = "/booking-calendar")
    public ResRS getBookingTimeline(@RequestParam(name = "filter") String filter) {

        var responses = dashboardSV.getBookingTimeline(filter.toLowerCase());
        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get top sellers report by company id and filter
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

        var response = dashboardSV.getBookingTopSeller(filter.toLowerCase(), startDate.toString(), endDate.toString(), 5);

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

        var responses = dashboardSV.getBookingActivity(filter.toLowerCase(), startDate.toString(), endDate.toString(), 5, headerBean.getLocalizationId());

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get staffs overview
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/summary-report")
    public ResRS getSummaryReport(@RequestParam(name = "filter", defaultValue = "weekly") String filter,
                                  @RequestParam(name = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                  @RequestParam(name = "endDate", defaultValue = "#{T(java.time.LocalDate).now()}", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        return localization.resAPI(HttpStatus.OK,"res_succ", dashboardSV.getSummaryReport(filter.toLowerCase(), startDate.toString(), endDate.toString()));

    }

}
