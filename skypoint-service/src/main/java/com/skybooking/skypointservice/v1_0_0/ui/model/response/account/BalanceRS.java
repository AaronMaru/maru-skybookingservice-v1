package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRS {
    protected BigDecimal topup;
    protected BigDecimal withdrawal;
    protected BigDecimal earning;
    protected BigDecimal earningExtra;
    protected BigDecimal refund;
    protected BigDecimal balance;
    protected BigDecimal savedPoint;
    protected String levelName;
    protected String levelCode;

    public BalanceRS() {

    }

    public BalanceRS(BalanceRS balanceRS) {
        topup = AmountFormatUtil.roundAmount(balanceRS.getTopup());
        withdrawal = AmountFormatUtil.roundAmount(balanceRS.getWithdrawal());
        earning = AmountFormatUtil.roundAmount(balanceRS.getEarning());
        earningExtra = AmountFormatUtil.roundAmount(balanceRS.getEarningExtra());
        refund = AmountFormatUtil.roundAmount(balanceRS.getRefund());
        balance = AmountFormatUtil.roundAmount(balanceRS.getBalance());
        savedPoint = AmountFormatUtil.roundAmount(balanceRS.getSavedPoint());
        levelName = balanceRS.getLevelName();
        levelCode = balanceRS.getLevelCode();
    }
}
