package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DashboardSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get recently dashboard by company and user id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param take
     * @return List
     */
    List<RecentBookingRS> getRecentBooking(long companyId, long userId, String userType, String userRole, long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard progress summary by company id, user id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param filter
     * @param startDate
     * @param endDate
     * @return List
     */
    List<BookingProgressRS> getBookingProgress(long companyId, long userId, String userType, String userRole, String filter, String startDate, String endDate);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard timeline summary report by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param filter
     * @return List
     */
    List<BookingTimelineRS> getBookingTimeline(long companyId, long userId, String userType, String userRole, String filter);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get top staff for selling dashboard by comapny id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param filter    options: range, daily, weekly, monthly, yearly
     * @param startDate required when filter by range and format "yyyy-MM-dd"
     * @param endDate   required when filter by range and format "yyyy-MM-dd"
     * @param take
     * @return List
     */
    List<BookingTopSellerRS> getBookingTopSeller(long companyId, String filter, String startDate, String endDate, long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard activity log by company, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param filter
     * @param startDate
     * @param endDate
     * @param take
     * @param localeId
     * @return List
     */
    List<BookingActivityRS> getBookingActivity(long companyId, long userId, String userType, String userRole, String filter, String startDate, String endDate, long take, long localeId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param company
     * @param userId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return BookingReportRS
     */
    BookingReportRS getBookingReport(long company, long userId, String userType, String userRole, String classType, String tripType, String startDate, String endDate);

}
