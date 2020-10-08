package com.skybooking.skypointservice.v1_0_0.io.repository.topUp;

import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopUpDocumentRP extends JpaRepository<TopUpDocumentEntity, Integer> {
    TopUpDocumentEntity findByTransactionId(Integer transactionId);

    List<TopUpDocumentEntity> findAllByTransactionIdAndType(Integer transactionId, String type);

    TopUpDocumentEntity findByTransactionIdAndLanguageCode(Integer transactionId, String lang);

}
