package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NativeQueryFolder("report")
@Component
public interface ReportNQ extends NativeQuery {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get report summary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return ReportSummaryTO
     */
    @Transactional(readOnly = true)
    ReportSummaryTO getReportSummary(@NativeQueryParam(value = "companyId") long companyId,
                                     @NativeQueryParam(value = "skyuserId") long skyuserId,
                                     @NativeQueryParam(value = "userType") String userType,
                                     @NativeQueryParam(value = "userRole") String userRole,
                                     @NativeQueryParam(value = "classType") String classType,
                                     @NativeQueryParam(value = "tripType") String tripType,
                                     @NativeQueryParam(value = "startDate") String startDate,
                                     @NativeQueryParam(value = "endDate") String endDate,
                                     @NativeQueryParam(value = "skystaffId") long skystaffId
                                     );


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get report detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly = true)
    List<ReportChartTO> getReportDetail(@NativeQueryParam(value = "companyId") long companyId,
                                        @NativeQueryParam(value = "skyuserId") long skyuserId,
                                        @NativeQueryParam(value = "userType") String userType,
                                        @NativeQueryParam(value = "userRole") String userRole,
                                        @NativeQueryParam(value = "classType") String classType,
                                        @NativeQueryParam(value = "tripType") String tripType,
                                        @NativeQueryParam(value = "startDate") String startDate,
                                        @NativeQueryParam(value = "endDate") String endDate,
                                        @NativeQueryParam(value = "skystaffId") long skystaffId
                                        );

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get report detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param companyId
     * @param skyuserId
     * @param userType
     * @param userRole
     * @param classType options: allclass, economy, first, business
     * @param tripType  options: alltrip, oneway, return, multicity
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly = true)
    Page<ReportListTO> getReportListing(@NativeQueryParam(value = "companyId") long companyId,
                                        @NativeQueryParam(value = "skyuserId") long skyuserId,
                                        @NativeQueryParam(value = "userType") String userType,
                                        @NativeQueryParam(value = "userRole") String userRole,
                                        @NativeQueryParam(value = "classType") String classType,
                                        @NativeQueryParam(value = "tripType") String tripType,
                                        @NativeQueryParam(value = "startDate") String startDate,
                                        @NativeQueryParam(value = "endDate") String endDate,
                                        @NativeQueryParam(value = "skystaffId") long skystaffId,
                                        @NativeQueryParam(value = "ONEWAY") String ONEWAY,
                                        @NativeQueryParam(value = "ROUND") String ROUND,
                                        @NativeQueryParam(value = "MULTICITY") String MULTICITY,
                                        Pageable pageable
                                        );
}
