package com.skybooking.skyhistoryservice.v1_0_0.transformer.dashboard;

import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingReportDetailTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.BookingReportSummaryTO;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingReportDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.BookingReportRS;
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
     * @return BookingReportRS
     */
    public static BookingReportRS getResponse(BookingReportSummaryTO report) {

        var response = new BookingReportRS();
        BeanUtils.copyProperties(report, response);

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param detail
     * @return BookingReportDetailRS
     */
    public static BookingReportDetailRS getDetailResponse(BookingReportDetailTO detail) {

        var response = new BookingReportDetailRS();
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
    public static List<BookingReportDetailRS> getDetailResponseList(List<BookingReportDetailTO> detailList) {

        var responses = new ArrayList<BookingReportDetailRS>();
        for (BookingReportDetailTO detail : detailList) {
            responses.add(getDetailResponse(detail));
        }

        return responses;
    }
}
