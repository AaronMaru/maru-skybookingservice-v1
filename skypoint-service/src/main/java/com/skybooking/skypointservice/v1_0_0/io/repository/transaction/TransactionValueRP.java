package com.skybooking.skypointservice.v1_0_0.io.repository.transaction;

import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionValueRP extends JpaRepository<TransactionValueEntity, Integer> {
    TransactionValueEntity findByTransactionIdAndTransactionTypeCode(Integer transactionId, String transactionTypeCode);
}
