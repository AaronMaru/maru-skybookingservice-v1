package com.skybooking.skyhistoryservice.v1_0_0.service.implment.dashboard;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingProgressTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.DashboardNQ;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard.DashboardSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.BookingActivityTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.BookingTimelineTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.BookingTopSellerTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.RecentBookingTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.report.BookingReportTF;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking.FilterRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.BookingReportRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HeaderBean headerBean;

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

        FilterRQ filter = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filter.getCompanyHeaderId());

        var bookings = dashboardNQ.getRecentBooking(filter.getCompanyHeaderId(), filter.getSkyuserId(), stake, filter.getRole(), take);

        var responses = RecentBookingTF.getResponseList(bookings);

        responses.forEach(response -> {
            var segments = dashboardNQ.getRecentBookingSegment(response.getBookingId());
            response.setSkyuserPhoto(environment.getProperty("spring.awsImageUrl.profile.url_small") + response.getSkyuserPhoto());

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
     * Get dashboard progress summary by companyConstant id, user id and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @param startDate
     * @param endDate
     * @return List
     */
    @Override
    public List<BookingProgressRS> getBookingProgress(String filter, String startDate, String endDate) {

        BookingKeyConstant constant = new BookingKeyConstant();

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var bookings = dashboardNQ.getBookingProgress(
            filterRQ.getCompanyHeaderId(),
            filterRQ.getSkyuserId(),
            stake,
            filterRQ.getRole(),
            filter,
            startDate,
            endDate,
            constant.COMPLETED,
            constant.UPCOMING,
            constant.CANCELLED,
            constant.FAILED
        );

        var totalBookings = bookings.stream().reduce(0,
                (totalResult, booking) -> totalResult + booking.getAmount().intValueExact(), Integer::sum);

        var summary = new TreeMap<String, BookingProgressRS>();
        summary.put(constant.COMPLETED, new BookingProgressRS(constant.COMPLETED));
        summary.put(constant.UPCOMING, new BookingProgressRS(constant.UPCOMING));
        summary.put(constant.CANCELLED, new BookingProgressRS(constant.CANCELLED));
        summary.put(constant.FAILED, new BookingProgressRS(constant.FAILED));

        for (BookingProgressTO booking : bookings) {

            var status = booking.getStatusKey();
            var amount = booking.getAmount();
            Float percent = (amount.floatValue() * 100) / totalBookings;

            summary.get(status).setAmount(amount);
            summary.get(status).setPercentage(percent);

        }

        var responses = new ArrayList<BookingProgressRS>();
        summary.entrySet().forEach(response -> responses.add(response.getValue()));

        return responses;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard timeline summary report by companyConstant, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @return List
     */
    @Override
    public List<BookingTimelineRS> getBookingTimeline(String filter) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var bookings = dashboardNQ.getBookingTimeline(filterRQ.getCompanyHeaderId(), filterRQ.getSkyuserId(), stake, filterRQ.getRole(), filter);
        return BookingTimelineTF.getResponseList(bookings);

    }

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
    @Override
    public List<BookingTopSellerRS> getBookingTopSeller(String filter, String startDate, String endDate,
            long take) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());

        var sellers = dashboardNQ.getBookingTopSeller(filterRQ.getCompanyHeaderId(), filter, startDate, endDate, take).stream()
                .filter(seller -> seller.getTotalAmount().intValue() > 0).collect(Collectors.toList());

        sellers.forEach(seller -> seller.setUserProfile(
                environment.getProperty("spring.awsImageUrl.profile.url_small") + seller.getUserProfile()));

        return BookingTopSellerTF.getResponseList(sellers);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard activity log by companyConstant, user and filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param filter
     * @param startDate
     * @param endDate
     * @param take
     * @param localeId
     * @return List
     */
    @Override
    public List<BookingActivityRS> getBookingActivity(String filter, String startDate, String endDate, long take, long localeId) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var bookings = dashboardNQ.getBookingActivity(filterRQ.getCompanyHeaderId(), filterRQ.getSkyuserId(), stake, filterRQ.getRole(), filter, startDate, endDate,
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
     * Get dashboard report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @Return BookingReportRS
     */
    @Override
    public BookingReportRS getBookingReport(String classType, String tripType, String startDate, String endDate) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var summary = dashboardNQ.getBookingReportSummary(filterRQ.getCompanyHeaderId(), filterRQ.getSkyuserId(), stake, filterRQ.getRole(), classType, tripType,
                startDate, endDate);
        var response = BookingReportTF.getResponse(summary);

        if (summary != null) {
            var details = dashboardNQ.getBookingReportDetail(filterRQ.getCompanyHeaderId(), filterRQ.getSkyuserId(), stake, filterRQ.getRole(), classType, tripType,
                    startDate, endDate);
            response.setBookings(BookingReportTF.getDetailResponseList(details));
        }

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard overview staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return StaffReportRS
     */
    public StaffReportRS getStaffReport() {

        Long company =  Long.valueOf(request.getHeader("X-CompanyId"));
        var staffReportRS = new StaffReportRS();

        var totalStaffTO = dashboardNQ.getOverviewStaffReport(company);
        var totalStaffReportRS = new TotalStaffReportRS();
        BeanUtils.copyProperties(totalStaffTO, totalStaffReportRS);

        var staffsTO = dashboardNQ.getOverviewStaffReportListing(company);
        List<StaffReportListRS> staffsRS = new ArrayList<>();

        staffsTO.forEach(staffTO -> {
            var staffRS = new StaffReportListRS();
            BeanUtils.copyProperties(staffTO, staffRS);

            staffRS.setProfileImg(environment.getProperty("spring.awsImageUrl.profile.url_small") + staffTO.getProfileImg());

            staffsRS.add(staffRS);
        });

        staffReportRS.setTotalStaff(totalStaffReportRS);
        staffReportRS.setListStaff(staffsRS);

        return staffReportRS;
    }


}
