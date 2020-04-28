package com.skybooking.skyhistoryservice.v1_0_0.service.implment.dashboard;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingProgressTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingTimeLineTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingTimeLineWeeklyTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.DashboardNQ;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.summaryReport.StaffReportListTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.summaryReport.SummaryReportTO;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.dashboard.DashboardSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.BookingActivityTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.BookingTopSellerTF;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard.RecentBookingTF;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking.FilterRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.summaryReport.BookingPredictionRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.summaryReport.StaffReportListRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.summaryReport.SummaryReportRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardIP implements DashboardSV {

    @Autowired
    private DashboardNQ dashboardNQ;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private LocalizationBean localizationBean;


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
            Double percent = Double.valueOf(apiBean.decimalFormat.format((amount.doubleValue() * 100) / totalBookings));
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

        List<BookingTimeLineTO> bookingTimeLineTOS = dashboardNQ.getBookingTimeline(filterRQ.getCompanyHeaderId(), filterRQ.getSkyuserId(), stake, filterRQ.getRole());
        List<BookingTimelineRS> bookingTimelineRS = new ArrayList<>();

        Date today = new Date();
        var dateString = new SimpleDateFormat("yyyy-MM").format(today);

        if (filter.toLowerCase().equals("daily")) {
            int lastDay = getLastDay();
            for (int i = 1; i <= lastDay; i++) {

                BookingTimelineRS bookingTimelineRS1 = new BookingTimelineRS();

                String day = "0" + i;
                if (i > 9) {
                    day = "" + i;
                }

                String dateTime = dateString + "-" + day;

                /**
                 * Set booking amount inside date
                 */
                for (BookingTimeLineTO timeline : bookingTimeLineTOS) {

                    var createdAt = new SimpleDateFormat("yyyy-MM-dd").format(timeline.getDate());
                    if (createdAt.equals(dateTime)) {
                        bookingTimelineRS1.setTotal(timeline.getTotal());
                    }

                }

                bookingTimelineRS1.setDuringKey(localizationBean.multiLanguageRes("day") + " " + day);
                bookingTimelineRS1.setDate(dateTime + "T00:00:00+07:00");
                bookingTimelineRS.add(bookingTimelineRS1);

            }
        }

        if (filter.toLowerCase().equals("weekly")) {

            int j = 1;
            for (int i = 1; i <= 4; i++) {


                String weekDate =  dateString+"-0" + j;
                if (j > 9) {
                    weekDate =  dateString+"-" + j;
                }

                BookingTimeLineWeeklyTO bookingTimeLineWeeklyTO = dashboardNQ.getBookingTimelineWeekly(
                                filterRQ.getCompanyHeaderId(),
                                filterRQ.getSkyuserId(),
                                stake,
                                filterRQ.getRole(),
                                dateString,
                                i
                            );


                BookingTimelineRS bookingTimelineRS1 = new BookingTimelineRS();
                bookingTimelineRS1.setDate(weekDate + "T00:00:00+07:00");
                bookingTimelineRS1.setTotal(bookingTimeLineWeeklyTO.getTotal());
                bookingTimelineRS1.setDuringKey(localizationBean.multiLanguageRes("week") + " " + i);

                bookingTimelineRS.add(bookingTimelineRS1);

                j += 7;

            }

        }

        return bookingTimelineRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get last day of month
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public int getLastDay() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        calendar.setTime(lastDayOfMonth);

        return calendar.get(Calendar.DAY_OF_MONTH);
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
    public BookingActivityPagingRS getBookingActivity(String filter, String startDate, String endDate, long take, long localeId) {

        BookingActivityPagingRS data = new BookingActivityPagingRS();
        BookingKeyConstant constant = new BookingKeyConstant();

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var bookings = dashboardNQ.getBookingActivity(
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
                        constant.FAILED,
                        filterRQ.getSkystaffId(),
                        PageRequest.of(filterRQ.getPage(), filterRQ.getSize())
                    );

        var responses = BookingActivityTF.getResponsePage(bookings);

        responses.forEach(response -> {

            response.setUserProfile(
                    environment.getProperty("spring.awsImageUrl.profile.url_small") + response.getUserProfile());
            response.setLegs(BookingActivityTF.getResponseSegmentList(
                    dashboardNQ.getBookingActivityFlightSegment(response.getBookingId(), localeId)));
        });

        data.setData(responses);
        data.setSize(filterRQ.getSize());
        data.setPage(filterRQ.getPage() + 1);
        data.setTotals(bookings.getTotalElements());

        return data;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard overview staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return StaffReportRS
     */
    public List<SummaryReportRS> getSummaryReport(String filter, String startDate, String endDate) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        List<SummaryReportRS> summaryReportRS = new ArrayList<>();

        SummaryReportTO summaryReportTO = dashboardNQ.getSummaryReport(filterRQ.getCompanyHeaderId(), filter, startDate, endDate);
        List<StaffReportListTO> staffReportListTOS = dashboardNQ.getOverviewStaffReportListing(filterRQ.getCompanyHeaderId());

        /**
         * Set Staff Report
         */
        List<StaffReportListRS> staffReportListRS = new ArrayList<>();
        for (StaffReportListTO staffListing : staffReportListTOS) {
            StaffReportListRS staffReportListRS1 = new StaffReportListRS();
            BeanUtils.copyProperties(staffListing, staffReportListRS1);
            staffReportListRS1.setProfileImg(environment.getProperty("spring.awsImageUrl.profile.url_small") + staffListing.getProfileImg());
            staffReportListRS.add(staffReportListRS1);
        }

        SummaryReportRS summaryReportStaff = new SummaryReportRS();
        summaryReportStaff.setTitle(localizationBean.multiLanguageRes("total_staff"));
        summaryReportStaff.setColor("#86C9D6");
        summaryReportStaff.setQuantity(summaryReportTO.getTotalStaff());
        summaryReportStaff.setListStaff(staffReportListRS);
        summaryReportRS.add(summaryReportStaff);

        /**
         * Set Booking Report
         */
        BookingPredictionRS bookingPredictionRS = new BookingPredictionRS();
        predictionBooking(summaryReportTO.getLastDayBQty().doubleValue(), summaryReportTO.getTodayBookQty().doubleValue(), bookingPredictionRS);
        var percentageBooking = Double.valueOf(apiBean.decimalFormat.format(bookingPredictionRS.getPercentage()));

        SummaryReportRS summaryReportBooking = new SummaryReportRS();
        summaryReportBooking.setTitle(localizationBean.multiLanguageRes("total_booking"));
        summaryReportBooking.setColor("#AB78D4");
        summaryReportBooking.setQuantity(summaryReportTO.getTotalBookQty());
        summaryReportBooking.setPercentage(percentageBooking);
        summaryReportBooking.setPredictionStatus(bookingPredictionRS.getPredictionStatus());
        summaryReportRS.add(summaryReportBooking);


        /**
         * Set Booking Spent
         */
        predictionBooking(summaryReportTO.getLastDayBAmount().doubleValue(), summaryReportTO.getTodayBookAmount().doubleValue(), bookingPredictionRS);
        var percentageSpent = Double.valueOf(apiBean.decimalFormat.format(bookingPredictionRS.getPercentage()));
        SummaryReportRS summaryReportSpent = new SummaryReportRS();

        summaryReportSpent.setTitle(localizationBean.multiLanguageRes("total_spent"));
        summaryReportSpent.setColor("#EB4AC7");
        summaryReportSpent.setAmount(summaryReportTO.getTotalSpent());
        summaryReportSpent.setPercentage(percentageSpent);
        summaryReportSpent.setPredictionStatus(bookingPredictionRS.getPredictionStatus());
        summaryReportRS.add(summaryReportSpent);

        return summaryReportRS;

    }


    /**
     * Calculate prediction of summary report
     */
    public static void predictionBooking(Double lastDay, Double today, BookingPredictionRS bookingPredictionRS) {

        var prediction = today - lastDay;

        if (prediction < 0) {
            bookingPredictionRS.setPredictionStatus("DECREASE");

            Double decrease = 0.0;
            if (lastDay > 0) {
                decrease = (prediction / lastDay) * 100;
            }
            bookingPredictionRS.setPercentage(decrease);
        }

        if (prediction > 0) {
            bookingPredictionRS.setPredictionStatus("INCREASE");

            Double increase = 0.0;
            if (lastDay > 0) {
                increase = (prediction / lastDay) * 100;
            }
            bookingPredictionRS.setPercentage(increase);
        }

        if (prediction == 0) {
            bookingPredictionRS.setPredictionStatus("INCREASE");
            Double increase = 0.0;
            bookingPredictionRS.setPercentage(increase);
        }

    }


}
