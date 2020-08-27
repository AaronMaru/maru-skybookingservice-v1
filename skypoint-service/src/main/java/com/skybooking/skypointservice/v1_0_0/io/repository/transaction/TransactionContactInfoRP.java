package com.skybooking.skypointservice.v1_0_0.io.repository.transaction;

import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionContactInfoRP extends JpaRepository<TransactionContactInfoEntity, Integer> {
    TransactionContactInfoEntity findByTransactionId(Integer transactionId);
}
