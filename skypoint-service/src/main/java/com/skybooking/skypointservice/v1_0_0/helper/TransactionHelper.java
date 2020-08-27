package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.TopUpTypeConstant;
import com.skybooking.skypointservice.constant.TransactionStatusConstant;
import com.skybooking.skypointservice.util.GenerateTransactionCode;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransactionHelper {
    @Autowired
    private TransactionRP transactionRP;

    public TransactionEntity saveTransactionOnlineTopUp(TransactionEntity transaction, AccountEntity account, OnlineTopUpRQ onlineTopUpRQ,
                                                        BigDecimal fee, String paymentMethod, Integer stakeholderUserId,
                                                        Integer stakeholderCompanyId) {
        //======== Create transaction
        transaction.setCode(this.generateTransactionCode());
        transaction.setAccountId(account.getId());
        transaction.setAmount(onlineTopUpRQ.getAmount());
        transaction.setPaidAmount(onlineTopUpRQ.getAmount());
        transaction.setFee(fee);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setStakeholderUserId(stakeholderUserId);
        transaction.setStakeholderCompanyId(stakeholderCompanyId);
        transaction.setProceedBy(TopUpTypeConstant.ONLINE);
        transaction.setStatus(TransactionStatusConstant.PRE_TOP_UP);
        transaction.setCreatedBy(stakeholderUserId.toString());
        transaction.setCreatedAt(new Date());
        transactionRP.save(transaction);
        return transaction;

    }

    public TransactionEntity saveTransactionOfflineTopUp(TransactionEntity transaction, AccountEntity account,
                                                         OfflineTopUpRQ offlineTopUpRQ, String createdBy) {
        transaction.setCode(generateTransactionCode());
        transaction.setAccountId(account.getId());
        transaction.setAmount(offlineTopUpRQ.getAmount());
        transaction.setPaidAmount(offlineTopUpRQ.getAmount());
        transaction.setStakeholderCompanyId(offlineTopUpRQ.getStakeholderCompanyId());
        transaction.setStakeholderUserId(offlineTopUpRQ.getStakeholderUserId());
        transaction.setProceedBy(TopUpTypeConstant.OFFLINE);
        transaction.setStatus(TransactionStatusConstant.SUCCESS);
        transaction.setReferenceCode(offlineTopUpRQ.getReferenceCode());
        transaction.setRemark(offlineTopUpRQ.getRemark());
        transaction.setCreatedBy(createdBy);
        transaction.setCreatedAt(new Date());
        transactionRP.save(transaction);
        return transaction;

    }

    public String generateTransactionCode() {
        //========= Get last transaction
        TransactionEntity lastCode = transactionRP.findFirstByOrderByIdDesc();
        //========= Generate unique transactionCode
        String txnCode = GenerateTransactionCode.generateTransactionCodeUnique(
                "PTS",
                (lastCode != null) ? lastCode.getCode() : null,
                "3"
        );

        return txnCode;
    }
}
