package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.summaryReport.SummaryReportRS;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface DashboardSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get recently dashboard by company and user id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param take
     * @return List
     */
    List<RecentBookingRS> getRecentBooking(long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard progress summary by company id, user id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @param startDate
     * @param endDate
     * @return List
     */
    List<BookingProgressRS> getBookingProgress(String filter, String startDate, String endDate);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard timeline summary report by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @return List
     */
    List<BookingTimelineRS> getBookingTimeline(String filter);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get top staff for selling dashboard by comapny id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter    options: range, daily, weekly, monthly, yearly
     * @param startDate required when filter by range and format "yyyy-MM-dd"
     * @param endDate   required when filter by range and format "yyyy-MM-dd"
     * @param take
     * @return List
     */
    List<BookingTopSellerRS> getBookingTopSeller(String filter, String startDate, String endDate, long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard activity log by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @param startDate
     * @param endDate
     * @param take
     * @param localeId
     * @return List
     */
    BookingActivityPagingRS getBookingActivity(String filter, String startDate, String endDate, long take, long localeId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard overview staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return StaffReportRS
     */
    List<SummaryReportRS> getSummaryReport(String filter, String startDate, String endDate);

}
