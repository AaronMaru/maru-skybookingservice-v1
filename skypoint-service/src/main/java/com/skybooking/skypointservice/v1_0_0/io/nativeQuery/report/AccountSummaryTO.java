package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class AccountSummaryTO {
    protected BigInteger account;
    protected BigDecimal balance;
    protected BigDecimal savedPoint;

    public AccountSummaryTO() {

    }

    public AccountSummaryTO(AccountSummaryTO accountSummaryTO) {
        account = accountSummaryTO.getAccount();
        balance = AmountFormatUtil.roundAmount(accountSummaryTO.getBalance());
        savedPoint = AmountFormatUtil.roundAmount(accountSummaryTO.getSavedPoint());
    }
}
