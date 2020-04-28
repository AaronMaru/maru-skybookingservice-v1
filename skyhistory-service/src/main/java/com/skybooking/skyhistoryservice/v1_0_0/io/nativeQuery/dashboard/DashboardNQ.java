package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.summaryReport.StaffReportListTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.summaryReport.SummaryReportTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NativeQueryFolder("dashboard")
@Component
public interface DashboardNQ extends NativeQuery {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingTO> getBookings();


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get recently dashboard by companyConstant and user id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param stake
     * @param userRole
     * @param take
     * @return List
     */
    @Transactional(readOnly = true)
    List<RecentBookingTO> getRecentBooking(@NativeQueryParam(value = "companyId") long companyId,
                                           @NativeQueryParam(value = "skyuserId") long skyuserId,
                                           @NativeQueryParam(value = "stake") String stake,
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
     * Get dashboard progress summary list by user, companyConstant and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param stake
     * @param userRole
     * @param filter
     * @param startDate
     * @param endDate
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingProgressTO> getBookingProgress(@NativeQueryParam(value = "companyId") long companyId,
                                               @NativeQueryParam(value = "skyuserId") long skyuserId,
                                               @NativeQueryParam(value = "stake") String stake,
                                               @NativeQueryParam(value = "userRole") String userRole,
                                               @NativeQueryParam(value = "filter") String filter,
                                               @NativeQueryParam(value = "startDate") String startDate,
                                               @NativeQueryParam(value = "endDate") String endDate,
                                               @NativeQueryParam(value = "COMPLETED") String COMPLETED,
                                               @NativeQueryParam(value = "UPCOMING") String UPCOMING,
                                               @NativeQueryParam(value = "CANCELLED") String CANCELLED,
                                               @NativeQueryParam(value = "FAILED") String FAILED
    );


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard timeline report by companyConstant and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param stake
     * @param userRole
     * @return List
     */
    @Transactional(readOnly = true)
    List<BookingTimeLineTO> getBookingTimeline(@NativeQueryParam(value = "companyId") long companyId,
                                               @NativeQueryParam(value = "skyuserId") long skyuserId,
                                               @NativeQueryParam(value = "stake") String stake,
                                               @NativeQueryParam(value = "userRole") String userRole);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard timeline report by companyConstant and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param stake
     * @param userRole
     * @return List
     */
    @Transactional(readOnly = true)
    BookingTimeLineWeeklyTO getBookingTimelineWeekly(@NativeQueryParam(value = "companyId") long companyId,
                                                     @NativeQueryParam(value = "skyuserId") long skyuserId,
                                                     @NativeQueryParam(value = "stake") String stake,
                                                     @NativeQueryParam(value = "userRole") String userRole,
                                                     @NativeQueryParam(value = "dateString") String dateString,
                                                     @NativeQueryParam(value = "week") Integer week
    );

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
     * Get dashboard activity by companyConstant and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param skyuserId
     * @param filer
     * @param startDate
     * @param endDate
     * @return List
     */
    @Transactional(readOnly = true)
    Page<BookingActivityTO> getBookingActivity(@NativeQueryParam(value = "companyId") long id,
                                               @NativeQueryParam(value = "skyuserId") long skyuserId,
                                               @NativeQueryParam(value = "stake") String stake,
                                               @NativeQueryParam(value = "userRole") String userRole,
                                               @NativeQueryParam(value = "filter") String filer,
                                               @NativeQueryParam(value = "startDate") String startDate,
                                               @NativeQueryParam(value = "endDate") String endDate,
                                               @NativeQueryParam(value = "COMPLETED") String COMPLETED,
                                               @NativeQueryParam(value = "UPCOMING") String UPCOMING,
                                               @NativeQueryParam(value = "CANCELLED") String CANCELLED,
                                               @NativeQueryParam(value = "FAILED") String FAILED,
                                               @NativeQueryParam(value = "skystaffId") Long skystaffId,
                                               Pageable pageable);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard activity flight fareComponentSegment by dashboard and locale id
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
     * Get overview total staff report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @return
     */

    @Transactional(readOnly = true)
    SummaryReportTO getSummaryReport(@NativeQueryParam(value = "companyId") long companyId,
                                     @NativeQueryParam(value = "filter") String filer,
                                     @NativeQueryParam(value = "startDate") String startDate,
                                     @NativeQueryParam(value = "endDate") String endDate
    );

    @Transactional(readOnly = true)
    List<StaffReportListTO> getOverviewStaffReportListing(@NativeQueryParam(value = "companyId") long companyId);

}
