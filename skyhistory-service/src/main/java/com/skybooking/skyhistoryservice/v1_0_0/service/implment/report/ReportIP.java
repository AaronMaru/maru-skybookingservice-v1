package com.skybooking.skyhistoryservice.v1_0_0.service.implment.report;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report.ReportListTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report.ReportNQ;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.report.ReportSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.report.BookingReportTF;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.report.ReportRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportPaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportListRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportIP implements ReportSV {

    @Autowired
    private ReportNQ reportNQ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get dashboard report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @request reportRQ
     * @return OverviewBookingReportRS2
     */
    @Override
    public ReportRS getBookingReport(HttpServletRequest request) {

        ReportRQ reportRQ = new ReportRQ(request, jwtUtils.getUserToken());
        reportRQ.setUserType(headerBean.getCompanyId(reportRQ.getCompanyHeaderId()));

        var summary = reportNQ.getReportSummary(
                    reportRQ.getCompanyId(),
                    reportRQ.getUserId(),
                    reportRQ.getUserType(),
                    reportRQ.getUserRole().toUpperCase(),
                    reportRQ.getClassType().toUpperCase(),
                    reportRQ.getTripType().toUpperCase(),
                    reportRQ.getStartDate(),
                    reportRQ.getEndDate(),
                    reportRQ.getSkystaffId()
                    );

        var response = BookingReportTF.getResponse(summary);

        if (summary != null) {
            var details = reportNQ.getReportDetail(
                            reportRQ.getCompanyId(),
                            reportRQ.getUserId(),
                            reportRQ.getUserType(),
                            reportRQ.getUserRole().toUpperCase(),
                            reportRQ.getClassType().toUpperCase(),
                            reportRQ.getTripType().toUpperCase(),
                            reportRQ.getStartDate(),
                            reportRQ.getEndDate(),
                            reportRQ.getSkystaffId()
                        );

            response.setBookings(BookingReportTF.getDetailResponseList(details));
        }

        return response;
    }

    @Override
    public ReportPaginationRS getReportListing(HttpServletRequest request) {

        ReportRQ reportRQ = new ReportRQ(request, jwtUtils.getUserToken());
        reportRQ.setUserType(headerBean.getCompanyId(reportRQ.getCompanyHeaderId()));

        Page<ReportListTO> reportListTOS = reportNQ.getReportListing(
                                                    reportRQ.getCompanyId(),
                                                    reportRQ.getUserId(),
                                                    reportRQ.getUserType(),
                                                    reportRQ.getUserRole().toUpperCase(),
                                                    reportRQ.getClassType().toUpperCase(),
                                                    reportRQ.getTripType().toUpperCase(),
                                                    reportRQ.getStartDate(),
                                                    reportRQ.getEndDate(),
                                                    reportRQ.getSkystaffId(),
                                                    BookingKeyConstant.ONEWAY,
                                                    BookingKeyConstant.ROUND,
                                                    BookingKeyConstant.MULTICITY,
                                                    PageRequest.of(reportRQ.getPage(), reportRQ.getSize())
                                                );

        List<ReportListRS> reportListRS = new ArrayList<>();

        for (ReportListTO reportList : reportListTOS) {
            ReportListRS reportListRS1 = new ReportListRS();
            BeanUtils.copyProperties(reportList, reportListRS1);
            reportListRS.add(reportListRS1);
        }

        ReportPaginationRS pagination = new ReportPaginationRS();
        pagination.setSize(reportRQ.getSize());
        pagination.setPage(reportRQ.getPage() + 1);
        pagination.setListings(reportListRS);
        pagination.setTotals(reportListTOS.getTotalElements());

        return pagination;

    }
}
