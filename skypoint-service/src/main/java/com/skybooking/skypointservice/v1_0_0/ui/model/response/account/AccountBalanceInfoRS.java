package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.AccountInfo;

public class AccountBalanceInfoRS extends AccountInfo {
    public AccountBalanceInfoRS() {

    }

    public AccountBalanceInfoRS (AccountBalanceInfoRS accountBalanceInfoRS) {
        levelCode = accountBalanceInfoRS.getLevelCode();
        levelName = accountBalanceInfoRS.getLevelName();
        earning = AmountFormatUtil.roundAmount(accountBalanceInfoRS.getEarning());
        earningExtra = AmountFormatUtil.roundAmount(accountBalanceInfoRS.getEarningExtra());
        savedPoint = AmountFormatUtil.roundAmount(accountBalanceInfoRS.getSavedPoint());
        balance = AmountFormatUtil.roundAmount(accountBalanceInfoRS.getBalance());

    }
}
