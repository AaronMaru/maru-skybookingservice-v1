package com.skybooking.skyhistoryservice.v1_0_0.service.implment.dashboard;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingProgressTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.DashboardNQ;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard.DashboardSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.*;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class DashboardIP implements DashboardSV {

    @Autowired
    private DashboardNQ dashboardNQ;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get recently dashboard by company and user id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param take
     * @return List
     */
    @Override
    public List<RecentBookingRS> getRecentBooking(long take) {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);
        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        String userType = jwtUtils.getClaim("userType", String.class);
        String userRole = jwtUtils.getClaim("userRole", String.class);

        if (userType.equals("skyuser")) {
            throw new BadRequestException("sth_w_w", "");
        }

        var bookings = dashboardNQ.getRecentBooking(companyId, skyuserId, userType, userRole, take);

        var responses = RecentBookingTF.getResponseList(bookings);

        responses.forEach(response -> {
            var segments = dashboardNQ.getRecentBookingSegment(response.getBookingId());
            response.setContPhoto(
                    environment.getProperty("spring.awsImageUrl.profile.url_small") + response.getContPhoto());
            segments.forEach(segment -> {

                var recentSegment = new RecentBookingSegmentRS();
                recentSegment.setCode(segment.getDepLocode() + "-" + segment.getArrLocode());
                response.getSegments().add(recentSegment);

            });
        });

        return responses;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard progress summary by companyConstant id, user id and filter
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
    @Override
    public List<BookingProgressRS> getBookingProgress(long companyId, long userId, String userType, String userRole,
            String filter, String startDate, String endDate) {

        var bookings = dashboardNQ.getBookingProgress(companyId, userId, userType, userRole, filter, startDate,
                endDate);
        var totalBookings = bookings.stream().reduce(0,
                (totalResult, booking) -> totalResult + booking.getAmount().intValueExact(), Integer::sum);

        var summary = new TreeMap<String, BookingProgressRS>();
        summary.put("Completed", new BookingProgressRS("Completed"));
        summary.put("Upcoming", new BookingProgressRS("Upcoming"));
        summary.put("Cancelled", new BookingProgressRS("Cancelled"));
        summary.put("Failed", new BookingProgressRS("Failed"));

        for (BookingProgressTO booking : bookings) {

            var status = booking.getStatus();
            var amount = booking.getAmount();
            var percent = (amount.intValue() * 100) / totalBookings;

            summary.get(status).setAmount(amount);
            summary.get(status).setPercent(percent);

        }

        var responses = new ArrayList<BookingProgressRS>();
        summary.entrySet().forEach(response -> responses.add(response.getValue()));

        return responses;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard timeline summary report by companyConstant, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param userType
     * @param userRole
     * @param filter
     * @return List
     */
    @Override
    public List<BookingTimelineRS> getBookingTimeline(long companyId, long userId, String userType, String userRole,
            String filter) {
        var bookings = dashboardNQ.getBookingTimeline(companyId, userId, userType, userRole, filter);
        return BookingTimelineTF.getResponseList(bookings);
    }

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
    @Override
    public List<BookingTopSellerRS> getBookingTopSeller(long companyId, String filter, String startDate, String endDate,
            long take) {

        var sellers = dashboardNQ.getBookingTopSeller(companyId, filter, startDate, endDate, take).stream()
                .filter(seller -> seller.getTotalAmount().intValue() > 0).collect(Collectors.toList());

        sellers.forEach(seller -> seller.setUserProfile(
                environment.getProperty("spring.awsImageUrl.profile.url_small") + seller.getUserProfile()));

        return BookingTopSellerTF.getResponseList(sellers);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get dashboard activity log by companyConstant, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param userId
     * @param filter
     * @param startDate
     * @param endDate
     * @param take
     * @param localeId
     * @return List
     */
    @Override
    public List<BookingActivityRS> getBookingActivity(long companyId, long userId, String userType, String userRole,
            String filter, String startDate, String endDate, long take, long localeId) {

        var bookings = dashboardNQ.getBookingActivity(companyId, userId, userType, userRole, filter, startDate, endDate,
                take);
        var responses = BookingActivityTF.getResponseList(bookings);
        responses.forEach(response -> {
            response.setUserProfile(
                    environment.getProperty("spring.awsImageUrl.profile.url_small") + response.getUserProfile());
            response.setSegments(BookingActivityTF.getResponseSegmentList(
                    dashboardNQ.getBookingActivityFlightSegment(response.getBookingId(), localeId)));
        });

        return responses;

    }

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
    @Override
    public BookingReportRS getBookingReport(long company, long userId, String userType, String userRole,
            String classType, String tripType, String startDate, String endDate) {

        var summary = dashboardNQ.getBookingReportSummary(company, userId, userType, userRole, classType, tripType,
                startDate, endDate);
        var response = BookingReportTF.getResponse(summary);

        if (summary != null) {
            var details = dashboardNQ.getBookingReportDetail(company, userId, userType, userRole, classType, tripType,
                    startDate, endDate);
            response.setBookings(BookingReportTF.getDetailResponseList(details));
        }

        return response;
    }
}
