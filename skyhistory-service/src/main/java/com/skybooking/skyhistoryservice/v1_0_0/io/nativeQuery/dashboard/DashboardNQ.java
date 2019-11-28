package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NativeQueryFolder("dashboard")
@Component
public interface DashboardNQ extends NativeQuery {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingTO> getBookings();


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
    @Transactional(readOnly = true)
    List<RecentBookingTO> getRecentBooking(@NativeQueryParam(value = "companyId") long companyId,
                                           @NativeQueryParam(value = "userId") long userId,
                                           @NativeQueryParam(value = "userType") String userType,
                                           @NativeQueryParam(value = "userRole") String userRole,
                                           @NativeQueryParam(value = "take") long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get recently dashboard's fareComponentSegment list by dashboard id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return List
     */
    @Transactional(readOnly = true)
    List<RecentBookingSegmentTO> getRecentBookingSegment(@NativeQueryParam(value = "bookingId") long bookingId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard progress summary list by user, company and filter
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
    @Transactional(readOnly = true)
    List<BookingProgressTO> getBookingProgress(@NativeQueryParam(value = "companyId") long companyId,
                                               @NativeQueryParam(value = "userId") long userId,
                                               @NativeQueryParam(value = "userType") String userType,
                                               @NativeQueryParam(value = "userRole") String userRole,
                                               @NativeQueryParam(value = "filter") String filter,
                                               @NativeQueryParam(value = "startDate") String startDate,
                                               @NativeQueryParam(value = "endDate") String endDate);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard timeline report by company and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param filter
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingTimeLineTO> getBookingTimeline(@NativeQueryParam(value = "companyId") long companyId,
                                               @NativeQueryParam(value = "userId") long userId,
                                               @NativeQueryParam(value = "userType") String userType,
                                               @NativeQueryParam(value = "userRole") String userRole,
                                               @NativeQueryParam(value = "filter") String filter);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get top staff of selling dashboard
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param filter
     * @param startDate
     * @param endDate
     * @param take
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingTopSellerTO> getBookingTopSeller(@NativeQueryParam(value = "companyId") long companyId,
                                                 @NativeQueryParam(value = "filter") String filter,
                                                 @NativeQueryParam(value = "startDate") String startDate,
                                                 @NativeQueryParam(value = "endDate") String endDate,
                                                 @NativeQueryParam(value = "take") long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard activity by company and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param userId
     * @param filer
     * @param startDate
     * @param endDate
     * @param take
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingActivityTO> getBookingActivity(@NativeQueryParam(value = "companyId") long id,
                                               @NativeQueryParam(value = "userId") long userId,
                                               @NativeQueryParam(value = "userType") String userType,
                                               @NativeQueryParam(value = "userRole") String userRole,
                                               @NativeQueryParam(value = "filter") String filer,
                                               @NativeQueryParam(value = "startDate") String startDate,
                                               @NativeQueryParam(value = "endDate") String endDate,
                                               @NativeQueryParam(value = "take") long take);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard activity flight fareComponentSegment by dashboard and locale id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @param localeId
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingActivityFlightSegmentTO> getBookingActivityFlightSegment(@NativeQueryParam(value = "bookingId") long bookingId,
                                                                         @NativeQueryParam(value = "localeId") long localeId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return BookingReportSummaryTO
     */
    @Transactional(readOnly = true)
    BookingReportSummaryTO getBookingReportSummary(@NativeQueryParam(value = "companyId") long companyId,
                                                   @NativeQueryParam(value = "userId") long userId,
                                                   @NativeQueryParam(value = "userType") String userType,
                                                   @NativeQueryParam(value = "userRole") String userRole,
                                                   @NativeQueryParam(value = "classType") String classType,
                                                   @NativeQueryParam(value = "tripType") String tripType,
                                                   @NativeQueryParam(value = "startDate") String startDate,
                                                   @NativeQueryParam(value = "endDate") String endDate);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard report detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly = true)
    List<BookingReportDetailTO> getBookingReportDetail(@NativeQueryParam(value = "companyId") long companyId,
                                                       @NativeQueryParam(value = "userId") long userId,
                                                       @NativeQueryParam(value = "userType") String userType,
                                                       @NativeQueryParam(value = "userRole") String userRole,
                                                       @NativeQueryParam(value = "classType") String classType,
                                                       @NativeQueryParam(value = "tripType") String tripType,
                                                       @NativeQueryParam(value = "startDate") String startDate,
                                                       @NativeQueryParam(value = "endDate") String endDate);

}
