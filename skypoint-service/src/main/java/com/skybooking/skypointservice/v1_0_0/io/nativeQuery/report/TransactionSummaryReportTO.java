package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionSummaryReportTO {
    protected Date createdAt;
    protected BigDecimal topUp = BigDecimal.valueOf(0);
    protected BigDecimal earnedExtra = BigDecimal.valueOf(0);
    protected BigDecimal earnedFlight = BigDecimal.valueOf(0);
    protected BigDecimal earnedHotel = BigDecimal.valueOf(0);
    protected BigDecimal redeemedFlight = BigDecimal.valueOf(0);
    protected BigDecimal redeemedHotel = BigDecimal.valueOf(0);
    protected BigDecimal refunded = BigDecimal.valueOf(0);

    public TransactionSummaryReportTO(TransactionSummaryReportTO transactionSummaryReportTO) {
        createdAt = transactionSummaryReportTO.getCreatedAt();
        topUp = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getTopUp());
        earnedExtra = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getEarnedExtra());
        earnedFlight = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getEarnedFlight());
        earnedHotel = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getEarnedHotel());
        redeemedFlight = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getRedeemedFlight());
        redeemedHotel = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getRedeemedHotel());
        refunded = AmountFormatUtil.roundAmount(transactionSummaryReportTO.getRedeemedFlight());
    }

    public TransactionSummaryReportTO() {

    }
}
