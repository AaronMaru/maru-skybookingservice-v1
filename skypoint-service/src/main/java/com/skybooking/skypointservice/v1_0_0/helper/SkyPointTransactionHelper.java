package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.v1_0_0.io.entity.skyPointManagement.SkyPointTransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.skyPointManagement.SkyPointTransactionRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SkyPointTransactionHelper {
    @Autowired
    SkyPointTransactionRP skyPointTransactionRP;

    public void saveSkyPointTransaction(String userType, Integer stakeholderCompanyId, Integer stakeholderUserId,
                                        TransactionEntity transaction) {
        //========= Save skyPoint transaction for skyStaff
        SkyPointTransactionEntity skyPointTransaction = new SkyPointTransactionEntity();
        if (!userType.equalsIgnoreCase(UserTypeConstant.SKYUSER)) {
            skyPointTransaction.setStakeholderCompanyId(stakeholderCompanyId);
        }
        skyPointTransaction.setStakeholderUserId(stakeholderUserId);
        skyPointTransaction.setTransactionCode(transaction.getCode());
        skyPointTransaction.setCreatedAt(new Date());
        skyPointTransaction.setUpdatedAt(new Date());
        skyPointTransactionRP.save(skyPointTransaction);
    }

}
