package com.skybooking.skyhistoryservice.v1_0_0.service.implment.report;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report.ReportNQ;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report.ReportSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.report.BookingReportTF;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.BookingReportRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportIP implements ReportSV {

    @Autowired
    private ReportNQ reportNQ;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard report summary
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

        var summary = reportNQ.getBookingReportSummary(company, userId, userType, userRole.toUpperCase(), classType.toUpperCase(), tripType.toUpperCase(),
                startDate, endDate);
        var response = BookingReportTF.getResponse(summary);

        if (summary != null) {
            var details = reportNQ.getBookingReportDetail(company, userId, userType, userRole.toUpperCase(), classType.toUpperCase(), tripType.toUpperCase(),
                    startDate, endDate);
            response.setBookings(BookingReportTF.getDetailResponseList(details));
        }

        return response;
    }

}
