package com.skybooking.skypointservice.v1_0_0.ui.controller.report;

import com.skybooking.skypointservice.v1_0_0.service.report.ReportSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/report")
public class ReportController {
    @Autowired
    private ReportSV reportSV;

    @PreAuthorize("#oauth2.hasScope('transaction-summary-report-skypoint')")
    @RequestMapping(value = "/transaction-summary", method = RequestMethod.GET)
    public StructureRS transactionSummaryReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return reportSV.transactionSummaryReport(startDate, endDate);
    }

    @PreAuthorize("#oauth2.hasScope('backend-dashboard-report-skypoint')")
    @RequestMapping(value = "/backend-dashboard", method = RequestMethod.GET)
    public StructureRS backendDashboardReport() {
        return reportSV.backendDashboardReport();
    }

    @PreAuthorize("#oauth2.hasScope('backend-account-info-report-skypoint')")
    @RequestMapping(value = "/account/info/{userCode}", method = RequestMethod.POST)
    public StructureRS backendAccountInfoReport(HttpServletRequest httpServletRequest,
                                                @PathVariable(name = "userCode") String userCode) {
        return reportSV.backendAccountInfoReport(httpServletRequest, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('topup-point-summary-report-skypoint')")
    @RequestMapping(value = "/top-up/point/summary", method = RequestMethod.GET)
    public StructureRS topUpPointSummaryReport(
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.topUpPointSummaryReport(startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('topup-point-detail-report-skypoint')")
    @RequestMapping(value = "/top-up/point/detail", method = RequestMethod.GET)
    public StructureRS topUpPointDetailReport(
            HttpServletRequest httpServletRequest,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.topUpPointDetailReport(httpServletRequest, startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('spent-point-summary-report-skypoint')")
    @RequestMapping(value = "/spent/point/summary", method = RequestMethod.GET)
    public StructureRS spentPointSummaryReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.spentPointSummaryReport(startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('spent-point-detail-report-skypoint')")
    @RequestMapping(value = "/spent/point/detail", method = RequestMethod.GET)
    public StructureRS spentPointDetailReport(
            HttpServletRequest httpServletRequest,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.spentPointDetailReport(httpServletRequest, startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('earned-point-summary-report-skypoint')")
    @RequestMapping(value = "/earned/point/summary", method = RequestMethod.GET)
    public StructureRS earnedPointSummaryReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.earnedPointSummaryReport(startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('earned-point-detail-report-skypoint')")
    @RequestMapping(value = "/earned/point/detail", method = RequestMethod.GET)
    public StructureRS earnedPointDetailReport(
            HttpServletRequest httpServletRequest,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.earnedPointDetailReport(httpServletRequest, startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('refunded-point-summary-report-skypoint')")
    @RequestMapping(value = "/refunded/point/summary", method = RequestMethod.GET)
    public StructureRS refundedPointSummaryReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.refundedPointSummaryReport(startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('refunded-point-detail-report-skypoint')")
    @RequestMapping(value = "/refunded/point/detail", method = RequestMethod.GET)
    public StructureRS refundedPointDetailReport(
            HttpServletRequest httpServletRequest,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(value = "userCode", required = false, defaultValue = "all") String userCode) {
        return reportSV.refundedPointDetailReport(httpServletRequest, startDate, endDate, userCode);
    }

    @PreAuthorize("#oauth2.hasScope('upgraded-level-summary-report-skypoint')")
    @RequestMapping(value = "/upgraded/level/summary", method = RequestMethod.GET)
    public StructureRS upgradedLevelReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return reportSV.upgradedLevelReport(startDate, endDate);
    }

    @PreAuthorize("#oauth2.hasScope('summary-transaction-list-report-skypoint')")
    @RequestMapping(value = "/transaction/export", method = RequestMethod.GET)
    public StructureRS transactionReportExport(
            HttpServletRequest httpServletRequest,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return reportSV.transactionReportExport(httpServletRequest, startDate, endDate);
    }

}
