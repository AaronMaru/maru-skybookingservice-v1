package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.util.GenerateTransactionCodeUtil;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValueHelper {
    @Autowired
    private TransactionValueRP transactionValueRP;

    public TransactionValueEntity saveTransactionValue(TransactionEntity transaction, ConfigTopUpEntity configTopUp,
                                                       BigDecimal earningExtra, String transactionTypeCode) {
        TransactionValueEntity transactionValue = new TransactionValueEntity();
        transactionValue.setCode(generateTransactionValueCode());
        transactionValue.setTransactionId(transaction.getId());
        transactionValue.setAmount(transaction.getAmount());
        transactionValue.setExtraRate(configTopUp.getValue());
        transactionValue.setEarningAmount(earningExtra);
        transactionValue.setTransactionTypeCode(transactionTypeCode);
        return transactionValueRP.save(transactionValue);
    }

    public String generateTransactionValueCode() {
        //========= Get last transaction
        TransactionValueEntity lastCode = transactionValueRP.findFirstByOrderByIdDesc();
        //========= Generate unique transactionCode
        String txnCode = GenerateTransactionCodeUtil.generateTransactionCodeUnique(
                "PTS",
                (lastCode != null) ? lastCode.getCode() : null,
                "3"
        );

        return txnCode;
    }
}
