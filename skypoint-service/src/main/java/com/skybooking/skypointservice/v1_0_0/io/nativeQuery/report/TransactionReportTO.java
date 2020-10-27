package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionReportTO {
    private String transactionCode;
    private String referenceCode;
    private String userCode;
    private BigDecimal amount;
    private Date transactionDate;
    private String transactionTypeCode;
    private String transactionTypeName;
    private String remark;

    public TransactionReportTO() {

    }

    public TransactionReportTO(TransactionReportTO transactionReportTO) {
        transactionCode = transactionReportTO.getTransactionCode();
        referenceCode = transactionReportTO.getReferenceCode();
        userCode = transactionReportTO.getUserCode();
        amount = AmountFormatUtil.roundAmount(transactionReportTO.getAmount());
        transactionDate = transactionReportTO.getTransactionDate();
        transactionTypeCode = transactionReportTO.getTransactionTypeCode();
        transactionTypeName = transactionReportTO.getTransactionTypeName();
        remark = transactionReportTO.getRemark();
    }
}
