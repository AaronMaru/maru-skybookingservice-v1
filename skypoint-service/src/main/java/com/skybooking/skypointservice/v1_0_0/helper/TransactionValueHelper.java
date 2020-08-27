package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransactionValueHelper {
    @Autowired
    private TransactionValueRP transactionValueRP;

    public void saveTransactionValue(TransactionEntity transaction, ConfigTopUpEntity configTopUp,
                                     BigDecimal earningExtra, String transactionTypeCode) {
        TransactionValueEntity transactionValue = new TransactionValueEntity();
        transactionValue.setTransactionId(transaction.getId());
        transactionValue.setAmount(transaction.getAmount());
        transactionValue.setExtraRate(configTopUp.getValue());
        transactionValue.setEarningAmount(earningExtra);
        transactionValue.setTransactionTypeCode(transactionTypeCode);
        transactionValue.setCreatedAt(new Date());
        transactionValue.setUpdatedAt(new Date());
        transactionValueRP.save(transactionValue);
    }
}
