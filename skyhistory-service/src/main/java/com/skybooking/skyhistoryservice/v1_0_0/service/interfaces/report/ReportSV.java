package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportPaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ReportSV {

    ReportRS getBookingReport(HttpServletRequest request);

    ReportPaginationRS getReportListing(HttpServletRequest request);

}
