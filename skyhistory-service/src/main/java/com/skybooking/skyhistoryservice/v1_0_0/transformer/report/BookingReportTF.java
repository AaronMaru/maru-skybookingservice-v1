package com.skybooking.skyhistoryservice.v1_0_0.transformer.report;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report.ReportChartTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report.ReportSummaryTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportChartRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report.ReportRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingReportTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param report
     * @return OverviewBookingReportRS2
     */
    public static ReportRS getResponse(ReportSummaryTO report) {

        var response = new ReportRS();
        BeanUtils.copyProperties(report, response);

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param detail
     * @return ReportChartRS
     */
    public static ReportChartRS getDetailResponse(ReportChartTO detail) {

        var response = new ReportChartRS();
        BeanUtils.copyProperties(detail, response);

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param detailList
     * @return List
     */
    public static List<ReportChartRS> getDetailResponseList(List<ReportChartTO> detailList) {

        var responses = new ArrayList<ReportChartRS>();
        for (ReportChartTO detail : detailList) {
            responses.add(getDetailResponse(detail));
        }

        return responses;
    }
}
