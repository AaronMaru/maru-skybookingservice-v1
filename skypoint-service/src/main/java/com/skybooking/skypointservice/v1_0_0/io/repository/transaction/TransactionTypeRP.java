package com.skybooking.skypointservice.v1_0_0.io.repository.transaction;

import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionTypeRP extends JpaRepository<TransactionTypeEntity, Integer> {

    @Query(value = "SELECT * FROM transaction_types WHERE code != 'EARNED_EXTRA' AND language_code = :languageCode", nativeQuery = true)
    List<TransactionTypeEntity> findAllByLanguageCode(String languageCode);
}
