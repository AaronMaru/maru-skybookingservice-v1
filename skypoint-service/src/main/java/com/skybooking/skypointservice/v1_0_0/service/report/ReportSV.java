package com.skybooking.skypointservice.v1_0_0.service.report;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public interface ReportSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Dashboard report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS backendDashboardReport();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Account info report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param userCode           String
     * @return StructureRS
     */
    StructureRS backendAccountInfoReport(HttpServletRequest httpServletRequest, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Transaction summary and list report filter by startDate & endDate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @return StructureRS
     */
    StructureRS transactionSummaryReport(String startDate, String endDate);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Transaction summary report list filter by startDate & endDate
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @return StructureRS
     */
    StructureRS transactionSummaryReportList(String startDate, String endDate);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Top up point summary report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @param userCode  String
     * @return StructureRS
     */
    StructureRS topUpPointSummaryReport(String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Top up point detail report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param startDate          String
     * @param endDate            String
     * @param userCode           String
     * @return StructureRS
     */
    StructureRS topUpPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Spent point summary report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @param userCode  String
     * @return StructureRS
     */
    StructureRS spentPointSummaryReport(String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Spent point detail report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param startDate          String
     * @param endDate            String
     * @param userCode           String
     * @return StructureRS
     */
    StructureRS spentPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Earned point summary report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @param userCode  String
     * @return StructureRS
     */
    StructureRS earnedPointSummaryReport(String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Earned point detail report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param startDate          String
     * @param endDate            String
     * @param userCode           String
     * @return StructureRS
     */
    StructureRS earnedPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Refunded point summary report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate String
     * @param endDate   String
     * @param userCode  String
     * @return StructureRS
     */
    StructureRS refundedPointSummaryReport(String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Refunded point detail report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param userCode           String
     * @return StructureRS
     */
    StructureRS refundedPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Upgraded level report
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param startDate Date
     * @param endDate   Date
     * @return StructureRS
     */
    StructureRS upgradedLevelReport(String startDate, String endDate);


}
