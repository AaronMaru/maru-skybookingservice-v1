package com.skybooking.skypointservice.v1_0_0.io.repository.transaction;

import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRP extends JpaRepository<TransactionEntity, Integer> {
    TransactionEntity findByCode(String code);

    TransactionEntity findFirstByOrderByIdDesc();

}
