package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.BookingReportRS;
import org.springframework.stereotype.Service;

@Service
public interface ReportSV {
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
