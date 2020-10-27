package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

@Data
public class BalanceInfo extends BalanceRS {
    public BalanceInfo() {

    }

    public BalanceInfo(BalanceInfo balanceInfo) {
        topup = AmountFormatUtil.roundAmount(balanceInfo.getBalance());
        withdrawal = AmountFormatUtil.roundAmount(balanceInfo.getWithdrawal());
        earning = AmountFormatUtil.roundAmount(balanceInfo.getEarning());
        earningExtra = AmountFormatUtil.roundAmount(balanceInfo.getEarningExtra());
        refund = AmountFormatUtil.roundAmount(balanceInfo.getRefund());
        balance = AmountFormatUtil.roundAmount(balanceInfo.getBalance());
        savedPoint = AmountFormatUtil.roundAmount(balanceInfo.getSavedPoint());
        levelName = balanceInfo.getLevelName();
        levelCode = balanceInfo.getLevelCode();
    }
}
